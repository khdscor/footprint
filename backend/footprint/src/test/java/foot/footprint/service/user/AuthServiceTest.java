package foot.footprint.service.user;

import foot.footprint.domain.user.application.AuthService;
import foot.footprint.domain.user.dao.UserRepository;
import foot.footprint.domain.user.domain.AuthProvider;
import foot.footprint.domain.user.domain.Role;
import foot.footprint.domain.user.domain.User;
import foot.footprint.domain.user.dto.LoginRequest;
import foot.footprint.domain.user.dto.SignUpRequest;
import foot.footprint.domain.user.exception.AlreadyExistedEmailException;
import foot.footprint.domain.user.exception.NotExistsEmailException;
import foot.footprint.domain.user.exception.NotMatchPasswordException;
import foot.footprint.global.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

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

    @Mock
    private JwtTokenProvider tokenProvider;

    @Spy
    @InjectMocks
    private AuthService authService;

    private User user;
    @Test
    @DisplayName("로그인시")
    public void Login() {
        //given
        String testToken = "testtset";
        setUser();
        LoginRequest loginRequest = new LoginRequest("email", "password");
        given(userRepository.findByEmail("email")).willReturn(Optional.ofNullable(user));
        given(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).willReturn(true);
        given(tokenProvider.createAccessToken(any(), any())).willReturn(testToken);

        //when
        String token = authService.login(loginRequest);

        //then
        assertThat(token).isEqualTo(testToken);
    }

    @Test
    @DisplayName("로그인 실패할 경우")
    public void Login_IfNotExistsEmail() {
        //given
        LoginRequest loginRequest = new LoginRequest("email", "password");

        //when & then
        assertThatThrownBy(
                () -> authService.login(loginRequest))
                .isInstanceOf(NotExistsEmailException.class);

        //given
        setUser();
        given(userRepository.findByEmail("email")).willReturn(Optional.ofNullable(user));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when & then
        assertThatThrownBy(
                () -> authService.login(loginRequest))
                .isInstanceOf(NotMatchPasswordException.class);
    }

    @Test
    @DisplayName("회원가입 진행시")
    public void SignUp() {
        //given
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
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

    private void setUser(){
        user = User.builder()
                .id(20L)
                .email("test")
                .image_url(null)
                .provider_id("test")
                .provider(AuthProvider.google)
                .nick_name("tset")
                .role(Role.USER)
                .join_date(new Date())
                .password("password").build();
    }
}
