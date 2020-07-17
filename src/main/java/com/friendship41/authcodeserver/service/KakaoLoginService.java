package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.response.ProcessResultResponse;

public interface KakaoLoginService {
  ProcessResultResponse kakaoLogin(String code, String kakaoAppkey, String kakaoRedirectUri);
}
