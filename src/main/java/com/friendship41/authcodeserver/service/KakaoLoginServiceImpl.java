package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.db.KakaoMember;
import com.friendship41.authcodeserver.data.db.KakaoMemberRepository;
import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.kakao.KakaoTokenResponse;
import com.friendship41.authcodeserver.data.kakao.KakaoUserInfoResponse;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.data.type.JoinFromType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("KakaoService")
public class KakaoLoginServiceImpl implements KakaoLoginService {

  private static final String URI_KAKAO_OAUTH_TOKEN_CODE = "https://kauth.kakao.com/oauth/token";
  private static final String URI_KAKAO_GET_USER_INFO = "https://kapi.kakao.com/v2/user/me";

  @Autowired
  private KakaoMemberRepository kakaoMemberRepository;
  @Autowired
  private MemberService memberService;

  private RestTemplate restTemplate;

  public KakaoLoginServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public Member kakaoLogin(final String code, String kakaoAppkey, String kakaoRedirectUri) {
    HttpEntity<MultiValueMap<String, String>> request = this.buildKakaoOauthRequest(code, kakaoAppkey, kakaoRedirectUri);
    KakaoTokenResponse kakaoTokenResponse = null;
    try {
      kakaoTokenResponse = restTemplate.postForObject(new URI(URI_KAKAO_OAUTH_TOKEN_CODE), request,
          KakaoTokenResponse.class);
    } catch (URISyntaxException e) {
      log.error("kakaoLogin(final String code, String kakaoAppkey, String kakaoRedirectUri)" , e);
      return null;
    }

    if (kakaoTokenResponse == null) {
      log.error(ProcessResultResponse.RESULT_MESSAGE_NOT_VALID_KAKAO_URI);
      return null;
    }

    request = this.buildKakaoGetUserInfoRequest(kakaoTokenResponse);
    KakaoUserInfoResponse kakaoUserInfoResponse = null;
    try {
      kakaoUserInfoResponse = restTemplate.postForObject(new URI(URI_KAKAO_GET_USER_INFO), request,
          KakaoUserInfoResponse.class);
    } catch (URISyntaxException e) {
      log.error("kakaoLogin(final String code, String kakaoAppkey, String kakaoRedirectUri)" ,e);
      return null;
    }

    if (kakaoUserInfoResponse == null) {
      log.error(ProcessResultResponse.RESULT_MESSAGE_NOT_VALID_KAKAO_URI);
      return null;
    }

    Member dbMember = memberService.getMember(String.valueOf(kakaoUserInfoResponse.getId()));
    String newPassword = UUID.randomUUID().toString();
    if (dbMember == null) {
      Member member = Member.builder()
          .email(String.valueOf(kakaoUserInfoResponse.getId()))
          .name(kakaoUserInfoResponse.getKakao_account().getProfile().getNickname())
          .joinFrom(JoinFromType.KAKAO.toString())
          .password(newPassword)
          .build();
      dbMember = memberService.joinMember(member, JoinFromType.KAKAO);
    } else {
      dbMember.setPassword(new BCryptPasswordEncoder(4).encode(newPassword));
    }

    KakaoMember kakaoMember = KakaoMember.builder()
        .kakaoId(kakaoUserInfoResponse.getId())
        .kakaoAccessToken(kakaoTokenResponse.getAccess_token())
        .kakaoRefreshToken(kakaoTokenResponse.getRefresh_token())
        .build();
    kakaoMemberRepository.save(kakaoMember);

    return Member.builder()
        .memberNo(dbMember.getMemberNo())
        .password(newPassword)
        .joinFrom(dbMember.getJoinFrom())
        .email(dbMember.getEmail())
        .name(dbMember.getName())
        .build();
  }

  private HttpEntity<MultiValueMap<String, String>> buildKakaoOauthRequest(String code,
      String kakaoAppkey, String kakaoRedirectUri) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", kakaoAppkey);
    params.add("redirect_uri", kakaoRedirectUri);
    params.add("code", code);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8");

    return new HttpEntity<>(params, headers);
  }

  private HttpEntity<MultiValueMap<String, String>> buildKakaoGetUserInfoRequest(KakaoTokenResponse kakaoTokenResponse) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer {"+kakaoTokenResponse.getAccess_token()+"}");
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.add("Content-Type",
        MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset-UTF-8");
    return new HttpEntity<>(headers);
  }
}
