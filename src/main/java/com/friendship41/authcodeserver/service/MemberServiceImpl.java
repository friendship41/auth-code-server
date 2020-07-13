package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.MemberRepository;
import com.friendship41.authcodeserver.data.response.MemberResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
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
  public MemberResultResponse getLogginedMember() {
    return null;
  }

  @Override
  public MemberResultResponse getMemberInfo(final String id) {
    return null;
  }
}
