package foot.footprint.domain.member.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest extends AuthRequest{

    @NotBlank(message = "닉네임을 작성해주세요")
    @NotEmpty(message = "닉네임을 작성해주세요")
    @Size(min = 4, max = 15, message = "닉네임은 4~15 글자 사이로 작성해주세요")
    private String nickName;

    public SignUpRequest( String email,
         String password, String nickName) {
        super(email, password);
        this.nickName = nickName;
    }
}