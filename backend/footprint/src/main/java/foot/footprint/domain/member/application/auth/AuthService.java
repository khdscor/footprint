package foot.footprint.domain.member.application.auth;

import foot.footprint.domain.member.dto.authDto.AuthDto;

public interface AuthService {

    String process(AuthDto authDto);
}