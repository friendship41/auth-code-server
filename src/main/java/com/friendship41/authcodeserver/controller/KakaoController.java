package com.friendship41.authcodeserver.controller;

import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.service.KakaoLoginService;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    return this.kakaoLoginPageUri;
  }

  @RequestMapping("/oauthCode")
  public String getKakaoAuthCode(@RequestParam(value = "code") String code) {
    Member member = kakaoLoginService.kakaoLogin(code, kakaoAppKey, kakaoRedirectUri);
    if (member == null) {
      return "/errorPage";
    } else {
      return "/member/kakao-login?id="+member.getEmail()+"&code="+member.getPassword();
    }
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
