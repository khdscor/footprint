package foot.footprint.service.user;

import foot.footprint.domain.user.AuthProvider;
import foot.footprint.domain.user.Role;
import foot.footprint.domain.user.User;
import foot.footprint.dto.user.LoginRequest;
import foot.footprint.dto.user.SignUpRequest;
import foot.footprint.exception.AlreadyExistedEmailException;
import foot.footprint.exception.NotExistsException;
import foot.footprint.exception.NotMatchPasswordException;
import foot.footprint.repository.user.UserRepository;
import foot.footprint.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
