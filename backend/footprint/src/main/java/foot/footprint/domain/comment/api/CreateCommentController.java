package foot.footprint.domain.comment.api;

import foot.footprint.domain.comment.application.CreateCommentService;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CreateCommentRequest;
import foot.footprint.global.security.user.CustomUserDetails;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class CreateCommentController {

  private final CreateCommentService createCommentService;

  @PostMapping("/{articleId}/comments")
  public ResponseEntity<CommentResponse> createOnPublicArticle(
      @PathVariable("articleId") Long articleId,
      @RequestBody @Valid CreateCommentRequest createCommentRequest,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    CommentResponse commentResponse = createCommentService.create(articleId,
        createCommentRequest.getContent(), userDetails.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
  }
}