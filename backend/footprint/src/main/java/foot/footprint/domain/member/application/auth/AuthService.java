package foot.footprint.domain.member.application.auth;

import foot.footprint.domain.member.dto.authRequest.AuthRequest;

public interface AuthService {

    String process(AuthRequest authRequest);
}