package foot.footprint.domain.member.dto.authDto;

import foot.footprint.domain.member.dto.authRequest.SignUpRequest;
import lombok.Getter;

@Getter
public class SignUpDto extends AuthDto {

    private String nickName;

    public SignUpDto(String email, String nickName, String password) {
        super(email, password);
        this.nickName = nickName;
    }

    public static SignUpDto create(SignUpRequest signUpRequest) {
        return new SignUpDto(signUpRequest.getEmail(), signUpRequest.getNickName(), signUpRequest.getPassword());
    }
}