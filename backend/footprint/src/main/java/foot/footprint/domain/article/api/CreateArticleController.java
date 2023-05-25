package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.CreateArticleService;
import foot.footprint.domain.article.dto.CreateArticleRequest;
import foot.footprint.global.error.exception.WrongInputException;
import foot.footprint.global.security.user.CustomUserDetails;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class CreateArticleController {

  private final CreateArticleService createArticleService;

  @PostMapping("/create")
  public ResponseEntity<Void> create(@RequestBody @Valid CreateArticleRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    validateLocation(request.getLatitude(), request.getLongitude());
    Long articleId = createArticleService.create(request, userDetails.getId());

    if (request.isPublicMap()) {
      return buildArticleCreateResponse("/articles/public/" + articleId);
    }
    if (request.isPrivateMap()) {
      return buildArticleCreateResponse("/articles/private/" + articleId);
    }
    return buildArticleCreateResponse("/articles/grouped/" + articleId);
  }

  private void validateLocation(double lat, double lng) {
    if (lat >= 180 || lat <= -180 || lng >= 180 || lng <= -180) {
      throw new WrongInputException("위치 좌표가 범위를 넘었습니다.");
    }
  }

  private ResponseEntity<Void> buildArticleCreateResponse(String uri) {
    return ResponseEntity.created(URI.create(uri))
        .build();
  }
}