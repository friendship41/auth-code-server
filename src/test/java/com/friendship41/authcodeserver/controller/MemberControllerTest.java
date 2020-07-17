package com.friendship41.authcodeserver.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.friendship41.authcodeserver.common.MemberUserDetailsService;
import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.data.type.JoinFromType;
import com.friendship41.authcodeserver.service.MemberService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {MemberController.class})
public class MemberControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private MemberService memberService;
  @MockBean
  private MemberUserDetailsService memberUserDetailsService;

  private ProcessResultResponse successResponse;


  @BeforeEach
  public void setUpTest() {
    this.setUpSuccessMessage();
  }

  @Test
  public void 메인_회원가입_요청() throws Exception {
    Member requestMember = Member.builder()
        .email("qwe@qwe.com")
        .password("qwerasdf")
        .name("qwe")
        .build();
    System.out.println(new ObjectMapper().writeValueAsString(requestMember));
    given(memberService.joinMember(requestMember, JoinFromType.MAIN)).willReturn(Member.builder()
        .email("qwe@qwe.com")
        .password("qwerasdf")
        .name("qwe")
        .joinFrom("main")
        .build());

    mvc.perform(post("/join")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .accept(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestMember)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(new ObjectMapper().writeValueAsString(successResponse)));
  }

  private void setUpSuccessMessage() {
    this.successResponse = new ProcessResultResponse();
  }
}
