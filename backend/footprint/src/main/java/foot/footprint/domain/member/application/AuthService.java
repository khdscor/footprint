package foot.footprint.domain.member.application;

import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.domain.member.dto.authRequest.AuthRequest;
import foot.footprint.domain.member.dto.authRequest.LoginRequest;
import foot.footprint.domain.member.dto.authRequest.SignUpRequest;
import foot.footprint.domain.member.exception.AlreadyExistedEmailException;
import foot.footprint.domain.member.exception.NotMatchPasswordException;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public String login(AuthRequest authRequest) {
        LoginRequest loginRequest = (LoginRequest) authRequest;
        Member member = findMember(loginRequest);
        verifyPassword(loginRequest, member);
        return tokenProvider.createAccessToken(String.valueOf(member.getId()), Role.USER);
    }

    @Transactional
    public void signUp(AuthRequest authRequest) {
        SignUpRequest signUpRequest = (SignUpRequest) authRequest;
        verifyEmail(signUpRequest);
        Member member = Member.createMember(signUpRequest, passwordEncoder);
        memberRepository.saveMember(member);
    }

    private Member findMember(LoginRequest loginRequest) {
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