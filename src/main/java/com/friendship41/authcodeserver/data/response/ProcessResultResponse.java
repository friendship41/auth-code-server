package com.friendship41.authcodeserver.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProcessResultResponse {
  public static final String RESULT_CODE_SUCCESS = "200";
  public static final String RESULT_CODE_SERVER_ERROR = "500";
  public static final String RESULT_CODE_RESULT_NOT_FOUND = "501";

  public static final String RESULT_MESSAGE_SUCESS = "성공";
  public static final String RESULT_MESSAGE_SERVER_ERROR = "서버 에러";
  public static final String RESULT_MESSAGE_MEMBER_NOT_FOUND = "회원정보를 찾을 수 없습니다.";

  private String resultCode = RESULT_CODE_SUCCESS;
  private String resultMessage = RESULT_MESSAGE_SUCESS;
}
