package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.CreateArticleService;
import foot.footprint.domain.article.dto.CreateArticleRequest;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<Void> create(@RequestBody CreateArticleRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    Long articleId = createArticleService.create(request, userDetails.getId());

    if (request.isPublicMap()) {
      return buildArticleCreateResponse("/articles/public/" + articleId);
    }
    if (request.isPrivateMap()) {
      return buildArticleCreateResponse("/articles/private/" + articleId);
    }
    return buildArticleCreateResponse("/articles/grouped/" + articleId);
  }

  private ResponseEntity<Void> buildArticleCreateResponse(String uri) {
    return ResponseEntity.created(URI.create(uri))
        .build();
  }
}