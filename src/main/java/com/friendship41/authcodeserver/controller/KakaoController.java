package com.friendship41.authcodeserver.controller;

import com.friendship41.authcodeserver.service.KakaoLoginService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/kakao")
public class KakaoController {
  @Autowired
  private KakaoLoginService kakaoLoginService;

  @Value("${kakao_app_key}")
  private String kakaoAppKey;
  @Value("${kakao_redirect_uri}")
  private String kakaoRedirectUri;
  @Value("${kakao_response_type}")
  private String kakaoResponseType;

  private String kakaoLoginPageUri;

  @RequestMapping("/loginPage")
  public String redirectToKakaoLoginPage() {
    return kakaoLoginPageUri;
  }

  @RequestMapping("/oauthCode")
  public String getKakaoAuthCode(@RequestParam(value = "code") String code) {
    return null;
  }

  @PostConstruct
  public void setUpKakaoLoginPageUri() {
    StringBuilder stringBuilder = new StringBuilder("redirect:https://kauth.kakao.com/oauth/authorize?client_id=");
    stringBuilder.append(kakaoAppKey);
    stringBuilder.append("&redirect_uri=");
    stringBuilder.append(kakaoRedirectUri);
    stringBuilder.append("&response_type=");
    stringBuilder.append(kakaoResponseType);
    this.kakaoLoginPageUri = stringBuilder.toString();
  }
}
