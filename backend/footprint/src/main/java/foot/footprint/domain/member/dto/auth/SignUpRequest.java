package foot.footprint.domain.member.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest extends AuthRequest{

    @NotBlank
    @NotEmpty
    @Size(min = 4, max = 15)
    private String nickName;

    public SignUpRequest( String email,
         String password, String nickName) {
        super(email, password);
        this.nickName = nickName;
    }
}