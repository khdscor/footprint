package foot.footprint.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {

  private String nickName;
  private String email;
  private String password;
}