package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.Member;

public interface KakaoLoginService {
  Member kakaoLogin(String code, String kakaoAppkey, String kakaoRedirectUri);
}
