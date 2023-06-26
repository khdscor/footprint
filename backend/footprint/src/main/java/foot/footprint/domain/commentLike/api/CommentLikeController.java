package foot.footprint.domain.commentLike.api;

import foot.footprint.domain.commentLike.application.CommentLikeService;
import foot.footprint.global.aop.comment.CommentLog;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @CommentLog
    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> changeMyLike(@PathVariable("commentId") Long commentId,
        @PathVariable("articleId") Long articleId,
        @RequestParam(value = "hasiliked") Boolean hasILiked,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentLikeService.changeMyLike(commentId, articleId, hasILiked, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}