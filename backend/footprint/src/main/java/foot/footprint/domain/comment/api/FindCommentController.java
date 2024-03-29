package foot.footprint.domain.comment.api;

import foot.footprint.domain.comment.application.find.FindCommentService;
import foot.footprint.domain.comment.dto.CommentsOnPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindCommentController {

    private final FindCommentService findCommentService;

    @GetMapping("/comments/read/{articleId}/{cursorId}")
    public ResponseEntity<CommentsOnPageResponse> findCommentsOnPage(
        @PathVariable("articleId") Long articleId, @PathVariable("cursorId") Long cursorId) {
        if (cursorId < 0) {
            return ResponseEntity.ok().body(new CommentsOnPageResponse());
        }
        CommentsOnPageResponse response = findCommentService.findComments(articleId, cursorId);
        return ResponseEntity.ok().body(response);
    }
}