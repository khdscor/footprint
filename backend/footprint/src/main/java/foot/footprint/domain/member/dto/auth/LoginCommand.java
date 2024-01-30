package foot.footprint.domain.member.dto.auth;

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