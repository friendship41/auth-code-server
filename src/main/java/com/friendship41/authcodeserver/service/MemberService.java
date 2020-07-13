package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.Member;
import com.friendship41.authcodeserver.data.response.MemberResultResponse;

public interface MemberService {
  Member joinMemberFromMain(Member member);
  MemberResultResponse getLogginedMember();
  MemberResultResponse getMemberInfo(String id);
}
