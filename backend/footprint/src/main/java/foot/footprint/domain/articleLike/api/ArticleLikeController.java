package foot.footprint.domain.articleLike.api;

import foot.footprint.domain.articleLike.application.ArticleLikeService;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{mapType}/{articleId}/like")
public class ArticleLikeController {

  private final ArticleLikeService articleLikeService;

  @PostMapping
  public ResponseEntity<Void> changeMyLike(@PathVariable("articleId") Long articleId,
      @RequestParam(value = "hasiliked") Boolean hasILiked,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    ArticleLikeDto articleLikeDto = new ArticleLikeDto(articleId, userDetails.getId(), hasILiked);
    articleLikeService.changeArticleLike(articleLikeDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<Boolean> checkMyLike(@PathVariable("articleId") Long articleId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    boolean result = articleLikeService.checkMyLike(
        new ArticleLikeDto(articleId, userDetails.getId()));

    return ResponseEntity.ok().body(result);
  }
}