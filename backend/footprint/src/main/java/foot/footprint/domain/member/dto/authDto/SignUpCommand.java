package foot.footprint.domain.member.dto.authDto;

import foot.footprint.domain.member.dto.authRequest.SignUpRequest;
import lombok.Getter;

@Getter
public class SignUpCommand extends AuthCommand {

    private String nickName;

    public SignUpCommand(String email, String nickName, String password) {
        super(email, password);
        this.nickName = nickName;
    }

    public static SignUpCommand create(SignUpRequest signUpRequest) {
        return new SignUpCommand(signUpRequest.getEmail(), signUpRequest.getNickName(), signUpRequest.getPassword());
    }
}