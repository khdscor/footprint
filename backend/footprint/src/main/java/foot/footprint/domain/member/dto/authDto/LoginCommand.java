package foot.footprint.domain.member.dto.authDto;

import foot.footprint.domain.member.dto.authRequest.LoginRequest;
import lombok.Getter;

@Getter
public class LoginCommand extends AuthCommand {

    public LoginCommand(String email, String password) {
        super(email, password);
    }

    public static LoginCommand create(LoginRequest loginRequest){
        return new LoginCommand(loginRequest.getEmail(), loginRequest.getPassword());
    }
}