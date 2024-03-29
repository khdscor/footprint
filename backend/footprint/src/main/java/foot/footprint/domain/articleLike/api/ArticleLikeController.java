package foot.footprint.domain.articleLike.api;

import foot.footprint.domain.articleLike.application.ChangeArticleLikeService;
import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;
import foot.footprint.global.aop.article.ArticleLog;
import foot.footprint.global.domain.MapType;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleLikeController {

    private final ChangeArticleLikeService changePublicArticleLike;

    private final ChangeArticleLikeService changePrivateArticleLike;

    private final ChangeArticleLikeService changeGroupedArticleLike;

    public ArticleLikeController(
        @Qualifier("public") ChangeArticleLikeService changePublicArticleLike,
        @Qualifier("private") ChangeArticleLikeService changePrivateArticleLike,
        @Qualifier("grouped") ChangeArticleLikeService changeGroupedArticleLike) {
        this.changePublicArticleLike = changePublicArticleLike;
        this.changePrivateArticleLike = changePrivateArticleLike;
        this.changeGroupedArticleLike = changeGroupedArticleLike;
    }

    @ArticleLog
    @PostMapping("/articles/{mapType}/{articleId}/like")
    public ResponseEntity<Void> changeMyLike(@PathVariable("articleId") Long articleId,
        @PathVariable("mapType") String mapType,
        @RequestParam(value = "hasiliked") Boolean hasILiked,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ArticleLikeCommand articleLikeCommand = new ArticleLikeCommand(articleId, userDetails.getId(),
            hasILiked);
        changeArticleLike(MapType.from(mapType), articleLikeCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void changeArticleLike(MapType mapType, ArticleLikeCommand articleLikeCommand) {
        if (mapType == MapType.PUBLIC) {
            changePublicArticleLike.changeArticleLike(articleLikeCommand);
        }
        if (mapType == MapType.PRIVATE) {
            changePrivateArticleLike.changeArticleLike(articleLikeCommand);
        }
        if (mapType == MapType.GROUPED) {
            changeGroupedArticleLike.changeArticleLike(articleLikeCommand);
        }
    }
}