package com.friendship41.authcodeserver.data.response;

import com.friendship41.authcodeserver.data.db.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResultResponse extends ProcessResultResponse {
  private Member member;
}
