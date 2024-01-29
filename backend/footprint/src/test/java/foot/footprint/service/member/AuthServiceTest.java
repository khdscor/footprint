package foot.footprint.service.member;

import foot.footprint.domain.member.application.auth.LoginService;
import foot.footprint.domain.member.application.auth.SignUpService;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.dto.auth.LoginCommand;
import foot.footprint.domain.member.dto.auth.SignUpCommand;
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

    @Spy
    private PasswordEncoder passwordEncoder = new MockPasswordEncoder();

    @Mock
    private JwtTokenProvider tokenProvider;

    @Spy
    @InjectMocks
    private LoginService loginService;

    @Spy
    @InjectMocks
    private SignUpService signUpService;

    private Member member;

    @Test
    @DisplayName("로그인시")
    public void Login() {
        //given
        String testToken = "testtset";
        setUser();
        LoginCommand loginDto = new LoginCommand("email", "password");
        given(memberRepository.findByEmail("email")).willReturn(Optional.ofNullable(member));
        given(passwordEncoder.matches(loginDto.getPassword(), member.getPassword())).willReturn(
            true);
        given(tokenProvider.createAccessToken(any(), any())).willReturn(testToken);

        //when
        String token = loginService.process(loginDto);

        //then
        assertThat(token).isEqualTo(testToken);
    }

    @Test
    @DisplayName("로그인 실패할 경우")
    public void Login_IfNotExistsEmail() {
        //given
        LoginCommand loginDto = new LoginCommand("email", "password");

        //when & then
        assertThatThrownBy(
            () -> loginService.process(loginDto))
            .isInstanceOf(NotExistsException.class);

        //given
        setUser();
        given(memberRepository.findByEmail("email")).willReturn(Optional.ofNullable(member));
        given(passwordEncoder.matches(loginDto.getPassword(), member.getPassword())).willReturn(
            false);

        //when & then
        assertThatThrownBy(
            () -> loginService.process(loginDto))
            .isInstanceOf(NotMatchPasswordException.class);
    }

    @Test
    @DisplayName("회원가입 진행시")
    public void SignUp() {
        //given
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        SignUpCommand signUpDto = new SignUpCommand("nickName", "email", "password");
        given(memberRepository.saveMember(any())).willReturn(1L);
        given(memberRepository.existsByEmail(any())).willReturn(false);

        //when
        signUpService.process(signUpDto);

        //then
        verify(memberRepository, times(1)).saveMember(captor.capture());
        Member member = captor.getValue();
        assertThat(signUpDto.getNickName()).isEqualTo(member.getNick_name());
    }

    @Test
    @DisplayName("회원가입 진행시 이미 이메일이 가입되어 있는경우")
    public void SignUp_IfExistsEmail() {
        //given
        boolean test = true;
        SignUpCommand signUpDto = new SignUpCommand("nickName", "email", "password");
        given(memberRepository.existsByEmail(any())).willReturn(true);

        //when & then
        assertThatThrownBy(
            () -> signUpService.process(signUpDto))
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

    private class MockPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPassword) {
            return new StringBuilder(rawPassword).reverse().toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encode(rawPassword).equals(encodedPassword);
        }
    }
}