package foot.footprint.domain.comment.api;


import foot.footprint.domain.comment.application.edit.EditCommentService;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.EditCommentRequest;
import foot.footprint.global.aop.comment.CommentLog;
import foot.footprint.global.security.user.CustomUserDetails;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class EditCommentController {

    private final EditCommentService editCommentService;

    @CommentLog
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> editComment(
        @PathVariable("commentId") Long commentId,
        @RequestBody @Valid EditCommentRequest editCommentRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        editCommentService.edit(commentId, userDetails.getId(), editCommentRequest.getContent());
        return ResponseEntity.noContent()
            .build();
    }
}