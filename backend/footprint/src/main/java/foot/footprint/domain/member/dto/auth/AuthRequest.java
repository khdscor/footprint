package foot.footprint.domain.member.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthRequest {

    @NotBlank(message = "이메일을 입력해주세요")
    @NotEmpty(message = "이메일을 입력해주세요")
    @Size(min = 4, max = 30)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 30)
    private String password;
}
