package foot.footprint.domain.commentLike.api;

import foot.footprint.domain.commentLike.application.ChangeCommentLikeService;
import foot.footprint.domain.commentLike.dto.ChangeCommentLikeCommand;
import foot.footprint.global.aop.comment.CommentLog;
import foot.footprint.global.domain.MapType;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles/{mapType}/{articleId}/comments")
public class CommentLikeController {

    private final ChangeCommentLikeService changePublicCommentLike;

    private final ChangeCommentLikeService changePrivateCommentLike;

    private final ChangeCommentLikeService changeGroupedCommentLike;

    public CommentLikeController(
        @Qualifier("public") ChangeCommentLikeService changePublicCommentLike,
        @Qualifier("private") ChangeCommentLikeService changePrivateCommentLike,
        @Qualifier("grouped") ChangeCommentLikeService changeGroupedCommentLike) {
        this.changePublicCommentLike = changePublicCommentLike;
        this.changePrivateCommentLike = changePrivateCommentLike;
        this.changeGroupedCommentLike = changeGroupedCommentLike;
    }

    @CommentLog
    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> changeMyLike(@PathVariable("commentId") Long commentId,
        @PathVariable("articleId") Long articleId, @PathVariable("mapType") String mapType,
        @RequestParam(value = "hasiliked") Boolean hasILiked,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        changeCommentLike(MapType.from(mapType), commentId, articleId, userDetails.getId(),
            hasILiked);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void changeCommentLike(MapType mapType, Long commentId, Long articleId, Long memberId,
        Boolean hasILiked) {
        ChangeCommentLikeCommand command = new ChangeCommentLikeCommand(commentId, articleId, hasILiked, memberId);
        if (mapType == MapType.PUBLIC) {
            changePublicCommentLike.changeMyLike(command);
        }
        if (mapType == MapType.PRIVATE) {
            changePrivateCommentLike.changeMyLike(command);
        }
        if (mapType == MapType.GROUPED) {
            changeGroupedCommentLike.changeMyLike(command);
        }
    }
}