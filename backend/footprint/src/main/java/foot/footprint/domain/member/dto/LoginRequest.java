package foot.footprint.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {

  @Email
  @NotBlank
  @Size(min = 4, max = 30)
  private String email;
  @NotBlank
  @Size(min = 4, max = 30)
  private String password;
}