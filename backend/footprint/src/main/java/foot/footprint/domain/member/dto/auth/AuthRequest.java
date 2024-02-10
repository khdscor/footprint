package foot.footprint.domain.member.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthRequest {

    @NotBlank(message = "이메일을 입력해주세요")
    @NotEmpty(message = "이메일을 입력해주세요")
    @Size(min = 4, max = 30, message = "이메일은 4~30자 사이로 입력해주세요")
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 30, message = "비밀번호는 4~10자 사이로 입력해주세요")
    private String password;
}
