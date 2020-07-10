package com.friendship41.authcodeserver.controller.temp;

import com.friendship41.authcodeserver.data.temp.Member;
import com.friendship41.authcodeserver.data.temp.MemberRepository;
import com.friendship41.authcodeserver.data.temp.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

  @Autowired
  private MemberRepository memberRepository;

  @RequestMapping("/api/profile")
  public ResponseEntity<UserProfile> profile() {
    User user = (User) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    String email = user.getUsername() + "@myMail.com";
    UserProfile profile = new UserProfile(user.getUsername(), email);

    return ResponseEntity.ok(profile);
  }

  @RequestMapping("/api/member")
  @ResponseBody
  public Object getMember() {
    User user = (User) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    return memberRepository.findById(user.getUsername());
  }

  @RequestMapping("/test")
  @ResponseBody
  public Object test1() {
    return memberRepository.findAll();
  }
}
