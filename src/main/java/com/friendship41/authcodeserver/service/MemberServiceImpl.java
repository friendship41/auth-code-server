package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.MemberRepository;
import com.friendship41.authcodeserver.data.response.MemberResultResponse;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
  private static final Log LOG = LogFactory.getLog(MemberServiceImpl.class);

  @Autowired
  private MemberRepository memberRepository;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

  @Override
  public Member joinMemberFromMain(final Member member) {
    if (member.getJoinFrom() == null || member.getJoinFrom().equals("")) {
      member.setJoinFrom("main");
    }
    member.setPassword(encoder.encode(member.getPassword()));

    return memberRepository.save(member);
  }

  @Override
  public ProcessResultResponse getLogginedMember() {
    User user = null;
    try {
      user = (User) SecurityContextHolder
          .getContext()
          .getAuthentication()
          .getPrincipal();
    } catch (Exception e) {
      LOG.error(e);
      return ProcessResultResponse.makeErrorResponse(ProcessResultResponse.RESULT_CODE_RESULT_NOT_FOUND,
          ProcessResultResponse.RESULT_MESSAGE_NOT_LOGIN);
    }
    if (user == null) {
      LOG.error("로그인한 유저 정보를 찾을 수 없습니다.");
      return ProcessResultResponse.makeErrorResponse(ProcessResultResponse.RESULT_CODE_RESULT_NOT_FOUND,
          ProcessResultResponse.RESULT_MESSAGE_NOT_LOGIN);
    }

    Optional<Member> temp = memberRepository.findById(user.getUsername());
    if (!temp.isPresent()) {
      LOG.error("가입한 유저 정보를 찾을 수 없습니다.");
      return ProcessResultResponse.makeErrorResponse(ProcessResultResponse.RESULT_CODE_RESULT_NOT_FOUND,
          ProcessResultResponse.RESULT_MESSAGE_MEMBER_NOT_FOUND);
    }

    temp.get().setPassword(null);
    return MemberResultResponse.builder()
        .member(temp.get())
        .build();
  }

  @Override
  public ProcessResultResponse getMemberInfo(final String email) {
    Optional<Member> temp = memberRepository.findById(email);
    if (!temp.isPresent()) {
      LOG.error("가입한 유저 정보를 찾을 수 없습니다.");
      return ProcessResultResponse.makeErrorResponse(ProcessResultResponse.RESULT_CODE_RESULT_NOT_FOUND,
          ProcessResultResponse.RESULT_MESSAGE_MEMBER_NOT_FOUND);
    }

    temp.get().setPassword(null);
    return MemberResultResponse.builder()
        .member(temp.get())
        .build();
  }
}
