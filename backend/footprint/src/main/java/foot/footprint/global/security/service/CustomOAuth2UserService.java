package foot.footprint.global.security.service;

import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.global.security.exception.OAuth2AuthenticationProcessingException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.global.security.user.OAuthAttributes;
import foot.footprint.global.security.user.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String DUMMY_PASSWORD = "무의미한 패스워드";

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration()
            .getRegistrationId(); // OAuth 서비스 이름(ex. github, naver, google)
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

        // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
        UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);
        if (Objects.isNull(userProfile.getEmail()) || userProfile.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("해당하는 이메일이 존재하지 않습니다.");
        }
        Member member = saveOrUpdate(userProfile, registrationId);  // DB에 저장

        return CustomUserDetails.create(member, oAuth2User.getAttributes());
    }

    private Member saveOrUpdate(UserProfile userProfile, String registrationId) {
        Optional<Member> userOptional = memberRepository.findByEmail(userProfile.getEmail());
        Member member;
        if (userOptional.isPresent()) {
            member = userOptional.get();
        } else {
            Date date = new Date();
            member = Member.builder()
                .nick_name(userProfile.getName())
                .email(userProfile.getEmail())
                .provider(AuthProvider.valueOf(registrationId))
                .password(passwordEncoder.encode(DUMMY_PASSWORD))
                .provider_id(userProfile.getProviderId())
                .image_url(userProfile.getImageUrl())
                .join_date(date)
                .role(Role.USER)
                .build();
            memberRepository.saveMember(member);
        }

        return member;
    }
}