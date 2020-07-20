package com.friendship41.authcodeserver.config;

import com.friendship41.authcodeserver.service.MemberService;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

// 카카오 1회용 로그인 설정
public class KakaoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private RequestCache requestCache = new HttpSessionRequestCache();

  @Autowired
  private MemberService memberService;

  @Override
  public void onAuthenticationSuccess(final HttpServletRequest httpServletRequest,
      final HttpServletResponse httpServletResponse,
      final Authentication authentication) throws IOException, ServletException {
    SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
    if (httpServletRequest.getParameter("isKakao") != null
        && httpServletRequest.getParameter("isKakao").equals("true")) {
      memberService.changePassword(httpServletRequest.getParameter("username"), UUID.randomUUID().toString());
    }
    httpServletResponse.sendRedirect(savedRequest.getRedirectUrl());
  }
}
