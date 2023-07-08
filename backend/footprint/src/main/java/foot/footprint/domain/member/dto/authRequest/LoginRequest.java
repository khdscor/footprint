package foot.footprint.domain.member.dto.authRequest;

import lombok.Getter;

@Getter
public class LoginRequest extends AuthRequest {

    public LoginRequest(String email, String password) {
        super(email, password);
    }
}