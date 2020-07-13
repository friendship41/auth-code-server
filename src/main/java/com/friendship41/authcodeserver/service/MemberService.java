package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;

public interface MemberService {
  Member joinMemberFromMain(Member member);
  ProcessResultResponse getLogginedMember();
  ProcessResultResponse getMemberInfo(String email);
}
