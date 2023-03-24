package foot.footprint.security.service;

import foot.footprint.domain2.user.AuthProvider;
import foot.footprint.domain2.user.Role;
import foot.footprint.domain2.user.User;
import foot.footprint.exception.OAuth2AuthenticationProcessingException;
import foot.footprint.repository.user.UserRepository;
import foot.footprint.security.user.CustomUserDetails;
import foot.footprint.security.user.OAuthAttributes;
import foot.footprint.security.user.UserProfile;
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
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String DUMMY_PASSWORD = "무의미한 패스워드";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(github, google, naver)에서 가져온 유저 정보를 담고있음


        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름(ex. github, naver, google)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

        // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
        UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);
        if (Objects.isNull(userProfile.getEmail()) || userProfile.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("해당하는 이메일이 존재하지 않습니다.");
        }
        User user = saveOrUpdate(userProfile, registrationId);  // DB에 저장

        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    private User saveOrUpdate(UserProfile userProfile, String registrationId) {
        Optional<User> userOptional = userRepository.findByEmail(userProfile.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            Date date = new Date();
            user = User.builder()
                    .nick_name(userProfile.getName())
                    .email(userProfile.getEmail())
                    .provider(AuthProvider.valueOf(registrationId))
                    .password(passwordEncoder.encode(DUMMY_PASSWORD))
                    .provider_id(userProfile.getProviderId())
                    .image_url(userProfile.getImageUrl())
                    .join_date(date)
                    .role(Role.USER)
                    .build();
            userRepository.saveUser(user);
        }

        return user;
    }
}
