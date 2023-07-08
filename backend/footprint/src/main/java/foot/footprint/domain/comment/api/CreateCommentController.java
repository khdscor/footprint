package foot.footprint.domain.comment.api;

import foot.footprint.domain.comment.application.create.CreateCommentService;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CreateCommentRequest;
import foot.footprint.global.aop.article.ArticleLog;
import foot.footprint.global.domain.MapType;
import foot.footprint.global.security.user.CustomUserDetails;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class CreateCommentController {

    private final CreateCommentService createCommentOnPublicArticle;
    private final CreateCommentService createCommentOnPrivateArticle;
    private final CreateCommentService createCommentOnGroupedArticle;

    public CreateCommentController(
        @Qualifier("public") CreateCommentService createCommentOnPublicArticle,
        @Qualifier("private") CreateCommentService createCommentOnPrivateArticle,
        @Qualifier("grouped") CreateCommentService createCommentOnGroupedArticle) {
        this.createCommentOnPublicArticle = createCommentOnPublicArticle;
        this.createCommentOnPrivateArticle = createCommentOnPrivateArticle;
        this.createCommentOnGroupedArticle = createCommentOnGroupedArticle;
    }

    @ArticleLog
    @PostMapping("/{mapType}/{articleId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable("articleId") Long articleId,
        @RequestBody @Valid CreateCommentRequest createCommentRequest,
        @PathVariable("mapType") String mapType,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        CommentResponse commentResponse = createComment(MapType.from(mapType), articleId,
            createCommentRequest.getContent(), userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    private CommentResponse createComment(MapType mapType, Long articleId, String content,
        Long memberId) {
        if (mapType == MapType.PUBLIC) {
            return createCommentOnPublicArticle.createComment(articleId, content, memberId);
        }
        if (mapType == MapType.PRIVATE) {
            return createCommentOnPrivateArticle.createComment(articleId, content, memberId);
        }
        if (mapType == MapType.GROUPED) {
            return createCommentOnGroupedArticle.createComment(articleId, content, memberId);
        }
        return null;
    }
}