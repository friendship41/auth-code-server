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
public class KakaoUserInfoResponse {
  @JsonProperty
  private boolean secure_resource;
  @JsonProperty
  private Properties properties;
  @JsonProperty
  private int id;
  @JsonProperty
  private Kakao_account kakao_account;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class Properties {
    @JsonProperty
    private String nickname;
    @JsonProperty
    private String profile_image;
    @JsonProperty
    private String thumbnail_image;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class Kakao_account {
    @JsonProperty
    private boolean profile_needs_agreement;
    @JsonProperty
    private Profile profile;
    @JsonProperty
    private boolean email_needs_agreement;
    @JsonProperty
    private boolean is_email_valid;
    @JsonProperty
    private boolean is_email_verified;
    @JsonProperty
    private String email;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Profile {
      @JsonProperty
      private String nickname;
      @JsonProperty
      private String thumbnail_image_url;
      @JsonProperty
      private String profile_image_url;
    }
  }
}
