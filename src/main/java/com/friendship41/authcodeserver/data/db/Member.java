package com.friendship41.authcodeserver.data.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int memberNo;
  @NotNull
  @Size(min = 3, max = 50, message = "email은 3~50자로 입력해 주세요")
  private String email;
  @NotNull
  @Size(min = 8, message = "password는 8~20자로 입력해 주세요")
  private String password;
  @NotNull
  @Size(min = 3, max = 20, message = "이름은 3~20자로 입력해 주세요")
  private String name;
  private String joinFrom;
}
