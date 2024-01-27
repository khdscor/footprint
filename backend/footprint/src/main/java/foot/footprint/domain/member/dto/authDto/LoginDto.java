package foot.footprint.domain.member.dto.authDto;

import foot.footprint.domain.member.dto.authRequest.LoginRequest;
import lombok.Getter;

@Getter
public class LoginDto extends AuthDto{

    public LoginDto(String email, String password) {
        super(email, password);
    }

    public static LoginDto create(LoginRequest loginRequest){
        return new LoginDto(loginRequest.getEmail(), loginRequest.getPassword());
    }
}