package foot.footprint.domain.member.application.auth;

import foot.footprint.domain.member.dto.auth.AuthCommand;

public interface AuthService {

    String process(AuthCommand authCommand);
}