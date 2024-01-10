package foot.footprint.domain.member.application.auth;

import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.domain.member.dto.authDto.AuthDto;
import foot.footprint.domain.member.dto.authDto.LoginDto;
import foot.footprint.domain.member.exception.NotMatchPasswordException;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("login")
@RequiredArgsConstructor
public class LoginService implements AuthService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public String process(AuthDto authDto) {
        LoginDto loginDto = (LoginDto) authDto;
        Member member = findMember(loginDto.getEmail());
        verifyPassword(loginDto.getPassword(), member);
        return tokenProvider.createAccessToken(String.valueOf(member.getId()), Role.USER);
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new NotExistsException("존재하지 않는 이메일입니다."));
    }

    private void verifyPassword(String password, Member member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new NotMatchPasswordException("비밀번호가 틀렸습니다.");
        }
    }
}