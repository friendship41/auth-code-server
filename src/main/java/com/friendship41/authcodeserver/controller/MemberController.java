package com.friendship41.authcodeserver.controller;

import com.friendship41.authcodeserver.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
  @Autowired
  private MemberService memberService;


}
