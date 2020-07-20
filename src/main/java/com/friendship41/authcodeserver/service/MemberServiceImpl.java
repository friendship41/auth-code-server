package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.common.MemberUserDetailsService;
import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.db.MemberRepository;
import com.friendship41.authcodeserver.data.response.MemberResultResponse;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.data.type.JoinFromType;
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
  public Member joinMember(final Member member, final JoinFromType joinFromType) {
    switch (joinFromType) {
      case MAIN:
        member.setJoinFrom("MAIN");
        break;
      case KAKAO:
        member.setJoinFrom("KAKAO");
        break;
      default:
        return null;
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

    Optional<Member> member = memberRepository.findByEmail(user.getUsername());
    if (!member.isPresent()) {
      LOG.error("가입한 유저 정보를 찾을 수 없습니다.");
      return ProcessResultResponse.makeErrorResponse(ProcessResultResponse.RESULT_CODE_RESULT_NOT_FOUND,
          ProcessResultResponse.RESULT_MESSAGE_MEMBER_NOT_FOUND);
    }

    member.get().setPassword(null);
    return MemberResultResponse.builder()
        .member(member.get())
        .build();
  }

  @Override
  public ProcessResultResponse getMemberResponse(final String email) {
    Optional<Member> member = memberRepository.findByEmail(email);
    if (!member.isPresent()) {
      LOG.error("가입한 유저 정보를 찾을 수 없습니다.");
      return ProcessResultResponse.makeErrorResponse(ProcessResultResponse.RESULT_CODE_RESULT_NOT_FOUND,
          ProcessResultResponse.RESULT_MESSAGE_MEMBER_NOT_FOUND);
    }

    member.get().setPassword(null);
    return MemberResultResponse.builder()
        .member(member.get())
        .build();
  }

  @Override
  public Member getMember(final String email) {
    return memberRepository.findByEmail(email).isPresent() ? memberRepository.findByEmail(email).get() : null;
  }

  @Override
  public Member changePassword(final String email, final String password) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    if (!optionalMember.isPresent()) {
      return null;
    }
    Member member = optionalMember.get();
    member.setPassword(encoder.encode(password));
    return member;
  }
}
