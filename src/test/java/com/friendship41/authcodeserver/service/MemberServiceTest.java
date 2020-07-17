package com.friendship41.authcodeserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.db.MemberRepository;
import com.friendship41.authcodeserver.data.type.JoinFromType;
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
        .memberNo(0)
        .email("qwe@qwe")
        .name("name")
        .joinFrom("MAIN")
        .build();
    Member resultMember = memberService.joinMember(newMember, JoinFromType.MAIN);
    resultMember.setMemberNo(0);
    resultMember.setPassword(null);
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
