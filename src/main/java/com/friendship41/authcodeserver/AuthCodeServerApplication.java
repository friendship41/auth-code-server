package com.friendship41.authcodeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class AuthCodeServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthCodeServerApplication.class, args);
  }
  // TODO: Error 페이지 구성
  // TODO: 로그아웃 구현
}
