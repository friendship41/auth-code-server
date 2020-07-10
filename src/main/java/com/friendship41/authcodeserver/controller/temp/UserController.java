package com.friendship41.authcodeserver.controller.temp;

import com.friendship41.authcodeserver.data.temp.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
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
}
