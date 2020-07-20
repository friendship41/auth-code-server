package com.friendship41.authcodeserver.controller;

import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.data.type.JoinFromType;
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
import org.springframework.web.bind.annotation.RequestParam;
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

  @RequestMapping(value = "/member", method = RequestMethod.GET)
  @ResponseBody
  public ProcessResultResponse getMemberByEmail(
      @RequestParam("getBy") String getBy,
      @RequestParam(value = "email", required = false) String email,
      @RequestParam(value = "memberNo", required = false) int memberNo) {
    if (getBy.equalsIgnoreCase("memberNo")) {
      return memberService.getMemberResponse(memberNo);
    } else if (getBy.equalsIgnoreCase("email")) {
      return memberService.getMemberResponse(email);
    }
    return ProcessResultResponse.makeErrorResponse(
        ProcessResultResponse.RESULT_CODE_BAD_REQUEST,
        ProcessResultResponse.RESULT_MESSAGE_BAD_REQUEST);
  }

  @RequestMapping(value = "/join", method = RequestMethod.POST)
  @ResponseBody
  public ProcessResultResponse joinProcess(@RequestBody @Valid Member member) {
    memberService.joinMember(member, JoinFromType.MAIN);

    return new ProcessResultResponse();
  }
}
