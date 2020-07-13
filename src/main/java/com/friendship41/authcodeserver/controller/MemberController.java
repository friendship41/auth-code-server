package com.friendship41.authcodeserver.controller;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.service.MemberService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
  @Autowired
  private MemberService memberService;

  @RequestMapping("/loginForm")
  public String goToLoginForm() {
    return "member/login-form";
  }

  @RequestMapping(value = "/joinForm", method = RequestMethod.GET)
  public String goToJoinForm() {
    return "member/join-form";
  }

  @RequestMapping(value = "/join", method = RequestMethod.POST)
  @ResponseBody
  public ProcessResultResponse joinProcess(@RequestBody @Valid Member member) {
    memberService.joinMemberFromMain(member);

    return ProcessResultResponse.builder()
        .resultCode(ProcessResultResponse.RESULT_CODE_SUCCESS)
        .resultMessage(ProcessResultResponse.RESULT_MESSAGE_SUCESS)
        .build();
  }
}
