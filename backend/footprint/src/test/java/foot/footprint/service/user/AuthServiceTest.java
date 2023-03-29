package foot.footprint.service.user;

import foot.footprint.domain.user.application.AuthService;
import foot.footprint.domain.user.dao.UserRepository;
import foot.footprint.domain.user.domain.User;
import foot.footprint.domain.user.dto.SignUpRequest;
import foot.footprint.domain.user.exception.AlreadyExistedEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private AuthService authService;

    @Captor
    private  ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    @Test
    @DisplayName("")
    public void Login() {
    }

    @Test
    @DisplayName("회원가입 진행시")
    public void SignUp() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("nickName", "email", "password");
        given(userRepository.saveUser(any())).willReturn(1);
        given(userRepository.existsByEmail("email")).willReturn(false);
        given(passwordEncoder.encode("password")).willReturn("password");

        //when
        authService.signUp(signUpRequest);

        //then
        verify(userRepository, times(1)).saveUser(captor.capture());
        User user = captor.getValue();
        assertThat(signUpRequest.getNickName()).isEqualTo(user.getNick_name());
    }

    @Test
    @DisplayName("회원가입 진행시 이미 이메일이 가입되어 있는경우")
    public void SignUp_IfExistsEmail() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("nickName", "email", "password");
        given(userRepository.existsByEmail("email")).willReturn(true);

        //when & then
        assertThatThrownBy(
                () -> authService.signUp(signUpRequest))
                .isInstanceOf(AlreadyExistedEmailException.class);
    }
}
