package foot.footprint.domain.member.api;

import foot.footprint.domain.member.dto.AuthResponse;
import foot.footprint.domain.member.dto.authRequest.AuthRequest;
import foot.footprint.domain.member.dto.authRequest.LoginRequest;
import foot.footprint.domain.member.dto.authRequest.SignUpRequest;
import foot.footprint.domain.member.application.AuthService;
import foot.footprint.global.aop.auth.LoginLog;
import foot.footprint.global.aop.auth.SignUpLog;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @LoginLog
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody @Valid LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @SignUpLog
    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }
}