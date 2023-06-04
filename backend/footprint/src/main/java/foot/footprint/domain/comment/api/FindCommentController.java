package foot.footprint.domain.comment.api;

import foot.footprint.domain.comment.application.FindCommentService;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.global.security.user.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindCommentController {

  private final FindCommentService findCommentService;

  @GetMapping("articles/comments/{articleId}")
  public ResponseEntity<List<CommentResponse>> findComments(
      @PathVariable("articleId") Long articleId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    List<CommentResponse> comments = findCommentService.findComments(articleId, userDetails);

    return ResponseEntity.ok().body(comments);
  }
}