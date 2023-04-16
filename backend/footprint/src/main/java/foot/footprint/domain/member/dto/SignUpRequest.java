package foot.footprint.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {

  @NotBlank
  private String nickName;
  @Email
  @NotBlank
  private String email;
  @NotBlank
  private String password;
}