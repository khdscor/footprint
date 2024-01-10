package foot.footprint.domain.member.api;

import foot.footprint.domain.member.application.auth.AuthService;
import foot.footprint.domain.member.dto.AuthResponse;
import foot.footprint.domain.member.dto.authDto.LoginDto;
import foot.footprint.domain.member.dto.authDto.SignUpDto;
import foot.footprint.domain.member.dto.authRequest.LoginRequest;
import foot.footprint.domain.member.dto.authRequest.SignUpRequest;
import foot.footprint.global.aop.auth.LoginLog;
import foot.footprint.global.aop.auth.SignUpLog;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService loginService;

    private final AuthService signUpService;

    public AuthController(@Qualifier("login") AuthService loginService,
        @Qualifier("signup") AuthService signUpService) {
        this.loginService = loginService;
        this.signUpService = signUpService;
    }

    @LoginLog
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody @Valid LoginRequest loginRequest) {
        String token = loginService.process(LoginDto.create(loginRequest));
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @SignUpLog
    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody @Valid SignUpRequest signUpRequest) {
        signUpService.process(SignUpDto.create(signUpRequest));

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }
}