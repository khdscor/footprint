package foot.footprint.service.user;

import foot.footprint.domain.member.application.AuthService;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.dto.LoginRequest;
import foot.footprint.domain.member.dto.SignUpRequest;
import foot.footprint.domain.member.exception.AlreadyExistedEmailException;
import foot.footprint.domain.member.exception.NotMatchPasswordException;
import foot.footprint.global.error.exception.NotExistsException;
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
  private MemberRepository memberRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtTokenProvider tokenProvider;

  @Spy
  @InjectMocks
  private AuthService authService;

  private Member member;

  @Test
  @DisplayName("로그인시")
  public void Login() {
    //given
    String testToken = "testtset";
    setUser();
    LoginRequest loginRequest = new LoginRequest("email", "password");
    given(memberRepository.findByEmail("email")).willReturn(Optional.ofNullable(member));
    given(passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())).willReturn(true);
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
        .isInstanceOf(NotExistsException.class);

    //given
    setUser();
    given(memberRepository.findByEmail("email")).willReturn(Optional.ofNullable(member));
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
    ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
    SignUpRequest signUpRequest = new SignUpRequest("nickName", "email", "password");
    given(memberRepository.saveMember(any())).willReturn(1L);
    given(memberRepository.existsByEmail("email")).willReturn(false);
    given(passwordEncoder.encode("password")).willReturn("password");

    //when
    authService.signUp(signUpRequest);

    //then
    verify(memberRepository, times(1)).saveMember(captor.capture());
    Member member = captor.getValue();
    assertThat(signUpRequest.getNickName()).isEqualTo(member.getNick_name());
  }

  @Test
  @DisplayName("회원가입 진행시 이미 이메일이 가입되어 있는경우")
  public void SignUp_IfExistsEmail() {
    //given
    SignUpRequest signUpRequest = new SignUpRequest("nickName", "email", "password");
    given(memberRepository.existsByEmail("email")).willReturn(true);

    //when & then
    assertThatThrownBy(
        () -> authService.signUp(signUpRequest))
        .isInstanceOf(AlreadyExistedEmailException.class);
  }

  private void setUser() {
    member = Member.builder()
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