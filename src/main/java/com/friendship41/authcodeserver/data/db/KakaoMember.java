package com.friendship41.authcodeserver.data.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoMember {
  @Id
  private int kakaoId;
  private String kakaoAccessToken;
  private String kakaoRefreshToken;
}
