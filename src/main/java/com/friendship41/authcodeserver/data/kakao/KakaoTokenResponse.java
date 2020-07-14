package com.friendship41.authcodeserver.data.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoTokenResponse {
  @JsonProperty("access_token")
  private String access_token;
  @JsonProperty("token_type")
  private String token_type;
  @JsonProperty("refresh_token")
  private String refresh_token;
  @JsonProperty("expires_in")
  private int expires_in;
  @JsonProperty("scope")
  private String scope;
}
