package foot.footprint.domain.comment.api;

import foot.footprint.domain.comment.application.delete.DeleteCommentService;
import foot.footprint.global.aop.comment.CommentLog;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class DeleteCommentController {

    private final DeleteCommentService deleteCommentService;

    @CommentLog
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        deleteCommentService.delete(commentId, userDetails.getId());

        return ResponseEntity.noContent()
            .build();
    }
}