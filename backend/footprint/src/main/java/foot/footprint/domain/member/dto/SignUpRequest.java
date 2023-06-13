package foot.footprint.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {

    @NotBlank
    @NotEmpty
    @Size(min = 4, max = 15)
    private String nickName;

    @Email
    @NotBlank
    @NotEmpty
    @Size(min = 4, max = 30)
    private String email;

    @NotBlank
    @NotEmpty
    @Size(min = 4, max = 30)
    private String password;
}