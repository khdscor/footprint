package foot.footprint.domain.member.dto.auth;

import lombok.Getter;

@Getter
public class LoginRequest extends AuthRequest {

    public LoginRequest(String email, String password) {
        super(email, password);
    }
}