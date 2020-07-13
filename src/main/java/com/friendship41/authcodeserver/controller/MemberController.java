package com.friendship41.authcodeserver.controller;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.service.MemberService;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

  @RequestMapping(value = "/member/me", method = RequestMethod.GET)
  @ResponseBody
  public ProcessResultResponse getLoginedMember() {
    return memberService.getLogginedMember();
  }

  @RequestMapping(value = "/member/{email}", method = RequestMethod.GET)
  @ResponseBody
  public ProcessResultResponse getMemberByEmail(@PathVariable("email") @NotNull @NotEmpty String email) {
    return memberService.getMemberInfo(email);
  }

  @RequestMapping(value = "/join", method = RequestMethod.POST)
  @ResponseBody
  public ProcessResultResponse joinProcess(@RequestBody @Valid Member member) {
    memberService.joinMemberFromMain(member);

    return new ProcessResultResponse();
  }
}
