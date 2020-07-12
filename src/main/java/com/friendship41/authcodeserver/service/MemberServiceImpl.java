package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
  @Autowired
  private MemberRepository memberRepository;
}
