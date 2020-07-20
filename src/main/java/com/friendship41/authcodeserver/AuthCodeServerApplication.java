package com.friendship41.authcodeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthCodeServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthCodeServerApplication.class, args);
  }
  // TODO: 로그파일 잘 떨어지게 만들어야함
  // TODO: Error 페이지 구성
}
