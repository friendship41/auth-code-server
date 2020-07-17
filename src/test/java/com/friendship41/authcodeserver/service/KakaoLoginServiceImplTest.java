package com.friendship41.authcodeserver.service;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import com.friendship41.authcodeserver.data.db.KakaoMemberRepository;
import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.db.MemberRepository;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(value = {
    KakaoLoginServiceImpl.class,
    ResourceLoader.class,
    MemberServiceImpl.class
})
@ActiveProfiles("test")
class KakaoLoginServiceImplTest {
  @Autowired
  private KakaoLoginService kakaoLoginService;
  @Autowired
  private MockRestServiceServer mockRestServer;
  @Autowired
  private MemberService memberService;
  @MockBean
  private MemberRepository memberRepository;
  @MockBean
  private KakaoMemberRepository kakaoMemberRepository;
  @Autowired
  private ResourceLoader resourceLoader;

  @BeforeEach
  void setUp() {
  }

  @Test
  void kakaoLogin() {
    mockRestServer
        .expect(requestTo("https://kauth.kakao.com/oauth/token"))
        .andRespond(
            withSuccess(resourceLoader.getResource("classpath:test_JSON/res_kakao_oauth_token.json"),
                MediaType.APPLICATION_JSON));
    mockRestServer
        .expect(requestTo("https://kapi.kakao.com/v2/user/me"))
        .andRespond(
            withSuccess(resourceLoader.getResource("classpath:test_JSON/res_kakao_userinfo.json"),
                MediaType.APPLICATION_JSON));

    Member member = kakaoLoginService.kakaoLogin("ABCDE", "qwe",
        "http://localhost:42222/kakao/oauthCode");
    System.out.println(member);
  }


}
