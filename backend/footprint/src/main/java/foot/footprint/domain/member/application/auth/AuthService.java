package foot.footprint.domain.member.application.auth;

import foot.footprint.domain.member.dto.authDto.AuthCommand;

public interface AuthService {

    String process(AuthCommand authCommand);
}