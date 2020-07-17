package com.friendship41.authcodeserver.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProcessResultResponse {
  public static final String RESULT_CODE_SUCCESS = "200";
  public static final String RESULT_CODE_BAD_REQUEST = "400";
  public static final String RESULT_CODE_SERVER_ERROR = "500";
  public static final String RESULT_CODE_RESULT_NOT_FOUND = "501";

  public static final String RESULT_MESSAGE_SUCESS = "성공";
  public static final String RESULT_MESSAGE_BAD_REQUEST = "잘못된 요청입니다.";
  public static final String RESULT_MESSAGE_SERVER_ERROR = "서버 에러";
  public static final String RESULT_MESSAGE_MEMBER_NOT_FOUND = "회원정보를 찾을 수 없습니다.";
  public static final String RESULT_MESSAGE_NOT_LOGIN = "로그인한 유저가 없습니다.";
  public static final String RESULT_MESSAGE_NOT_VALID_KAKAO_URI = "카카오 URI가 잘못되었습니다.";

  private String resultCode = RESULT_CODE_SUCCESS;
  private String resultMessage = RESULT_MESSAGE_SUCESS;

  public static ProcessResultResponse makeErrorResponse(String resultCode, String resultMessage) {
    return new ProcessResultResponse(resultCode, resultMessage);
  }
}
