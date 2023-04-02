package foot.footprint.domain.user.api;

import foot.footprint.domain.user.dto.AuthResponse;
import foot.footprint.domain.user.dto.LoginRequest;
import foot.footprint.domain.user.dto.SignUpRequest;
import foot.footprint.domain.user.application.AuthService;
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

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> authenticateUser(
      @RequestBody LoginRequest loginRequest) {
    String token = authService.login(loginRequest);

    return ResponseEntity.ok(new AuthResponse(token));
  }

  @PostMapping("/signup")
  public ResponseEntity<Void> registerUser(@RequestBody SignUpRequest signUpRequest) {
    authService.signUp(signUpRequest);

    return ResponseEntity.status(HttpStatus.CREATED)
        .build();
  }
}