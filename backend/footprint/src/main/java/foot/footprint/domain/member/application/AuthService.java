package foot.footprint.domain.member.application;

import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.domain.member.dto.LoginRequest;
import foot.footprint.domain.member.dto.SignUpRequest;
import foot.footprint.domain.member.exception.AlreadyExistedEmailException;
import foot.footprint.domain.member.exception.NotMatchPasswordException;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;

  @Transactional(readOnly = true)
  public String login(LoginRequest loginRequest) {
    Member member = fidnMember(loginRequest);
    verifyPassword(loginRequest, member);
    return tokenProvider.createAccessToken(String.valueOf(member.getId()), Role.USER);
  }

  @Transactional
  public void signUp(SignUpRequest signUpRequest) {
    verifyEmail(signUpRequest);
    Member member = Member.builder()
        .email(signUpRequest.getEmail())
        .provider(AuthProvider.local)
        .nick_name(signUpRequest.getNickName())
        .role(Role.USER)
        .join_date(new Date())
        .password(passwordEncoder.encode(signUpRequest.getPassword())).build();
    memberRepository.saveMember(member);
  }

  private Member fidnMember(LoginRequest loginRequest) {
    return memberRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new NotExistsException("존재하지 않는 이메일입니다."));
  }

  private void verifyPassword(LoginRequest loginRequest, Member member) {
    if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
      throw new NotMatchPasswordException("비밀번호가 틀렸습니다.");
    }
  }

  private void verifyEmail(SignUpRequest signUpRequest) {
    if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new AlreadyExistedEmailException("이미 사용중인 이메일입니다.");
    }
  }
}