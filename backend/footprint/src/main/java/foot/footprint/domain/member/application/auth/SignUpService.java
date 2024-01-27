package foot.footprint.domain.member.application.auth;

import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.dto.authDto.AuthDto;
import foot.footprint.domain.member.dto.authDto.SignUpDto;
import foot.footprint.domain.member.exception.AlreadyExistedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("signup")
@RequiredArgsConstructor
public class SignUpService implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String process(AuthDto authDto) {
        SignUpDto signUpDto = (SignUpDto) authDto;
        verifyEmail(signUpDto.getEmail());
        Member member = Member.createMember(signUpDto.getEmail(), signUpDto.getNickName(),
                passwordEncoder.encode(signUpDto.getPassword()));
        memberRepository.saveMember(member);
        return "";
    }

    private void verifyEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new AlreadyExistedEmailException("이미 사용중인 이메일입니다.");
        }
    }
}