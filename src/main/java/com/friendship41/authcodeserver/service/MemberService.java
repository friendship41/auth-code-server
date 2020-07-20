package com.friendship41.authcodeserver.service;

import com.friendship41.authcodeserver.data.db.Member;
import com.friendship41.authcodeserver.data.response.ProcessResultResponse;
import com.friendship41.authcodeserver.data.type.JoinFromType;

public interface MemberService {
  Member joinMember(Member member, JoinFromType joinFromType);
  ProcessResultResponse getLogginedMember();
  ProcessResultResponse getMemberResponse(int memberNo);
  ProcessResultResponse getMemberResponse(String email);
  Member getMember(String email);
  Member changePassword(String email, String password);
}
