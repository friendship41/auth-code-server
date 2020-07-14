package com.friendship41.authcodeserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {
  @Autowired
  private MemberService memberService;
  @Autowired
  private MemberRepository memberRepository;

  private static Member newMember;

  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

  @BeforeEach
  public void setUpTest() {
    this.createNewMemberFromMain();
  }

  @Test
  public void 메인홈_회원가입테스트_성공() {
    Member correctMember = Member.builder()
        .email("qwe@qwe")
        .name("name")
        .joinFrom("main")
        .build();
    Member resultMember = memberService.joinMemberFromMain(newMember);
    assertThat(resultMember)
        .isEqualToIgnoringNullFields(correctMember);
  }

  private void createNewMemberFromMain() {
    this.newMember = Member.builder()
        .email("qwe@qwe")
        .password("qwerasdf")
        .name("name")
        .build();
  }
}
