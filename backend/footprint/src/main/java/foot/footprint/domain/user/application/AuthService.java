package foot.footprint.domain.user.application;

import foot.footprint.domain.user.domain.AuthProvider;
import foot.footprint.domain.user.domain.Role;
import foot.footprint.domain.user.domain.User;
import foot.footprint.domain.user.dto.LoginRequest;
import foot.footprint.domain.user.dto.SignUpRequest;
import foot.footprint.domain.user.exception.AlreadyExistedEmailException;
import foot.footprint.domain.user.exception.NotExistsEmailException;
import foot.footprint.domain.user.exception.NotMatchPasswordException;
import foot.footprint.domain.user.dao.UserRepository;
import foot.footprint.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotExistsEmailException("존재하지 않는 이메일입니다."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotMatchPasswordException("비밀번호가 틀렸습니다.");
        }
        return tokenProvider.createAccessToken(String.valueOf(user.getId()), Role.USER);
    }

    public void signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistedEmailException("이미 사용중인 이메일입니다.");
        }
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .provider(AuthProvider.local)
                .nick_name(signUpRequest.getNickName())
                .role(Role.USER)
                .join_date(new Date())
                .password(passwordEncoder.encode(signUpRequest.getPassword())).build();
        userRepository.saveUser(user);
    }
}
