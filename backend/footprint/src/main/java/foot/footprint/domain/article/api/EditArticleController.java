package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.EditArticleService;
import foot.footprint.domain.article.dto.EditArticleContentRequest;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class EditArticleController {

  private final EditArticleService editArticleService;

  @PutMapping("/{articleId}")
  public ResponseEntity<Void> editArticle(@PathVariable("articleId") Long articleId,
      @RequestBody EditArticleContentRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    editArticleService.edit(articleId, userDetails.getId(), request.getNewContent());
    return ResponseEntity.noContent()
        .build();
  }
}