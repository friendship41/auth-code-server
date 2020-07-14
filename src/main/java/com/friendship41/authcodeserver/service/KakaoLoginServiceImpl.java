package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.MemberRepository;
import com.friendship41.authcodeserver.data.kakao.KakaoTokenResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("KakaoService")
public class KakaoLoginServiceImpl implements KakaoLoginService {
  private static final Log LOG = LogFactory.getLog(KakaoLoginServiceImpl.class);

  private static final String URI_KAKAO_OAUTH_TOKEN_CODE = "https://kauth.kakao.com/oauth/token";

  @Autowired
  private MemberRepository memberRepository;

  private RestTemplate restTemplate;

  public KakaoLoginServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public Member kakaoLogin(final String code, String kakaoAppkey, String kakaoRedirectUri) {
    HttpEntity<MultiValueMap<String, String>> request = this.buildKakaoOauthRequest(code, kakaoAppkey, kakaoRedirectUri);
    KakaoTokenResponse response = null;
    try {
      response = restTemplate.postForObject(new URI(URI_KAKAO_OAUTH_TOKEN_CODE), request, KakaoTokenResponse.class);
    } catch (URISyntaxException e) {
      LOG.error(e);
      return null;
    }



    return null;
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
