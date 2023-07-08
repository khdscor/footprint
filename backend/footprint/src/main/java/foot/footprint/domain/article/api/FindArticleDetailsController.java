package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.findArticleDetails.FindArticleDetailsService;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.global.domain.MapType;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class FindArticleDetailsController {

    private final FindArticleDetailsService findPublicArticleDetails;

    private final FindArticleDetailsService findPrivateArticleDetails;

    private final FindArticleDetailsService findGroupedArticleDetails;

    public FindArticleDetailsController(
        @Qualifier("public") FindArticleDetailsService findPublicArticleDetails,
        @Qualifier("private") FindArticleDetailsService findPrivateArticleDetails,
        @Qualifier("grouped") FindArticleDetailsService findGroupedArticleDetails) {
        this.findPublicArticleDetails = findPublicArticleDetails;
        this.findPrivateArticleDetails = findPrivateArticleDetails;
        this.findGroupedArticleDetails = findGroupedArticleDetails;
    }

    @GetMapping("{mapType}/details/{articleId}")
    public ResponseEntity<ArticlePageResponse> findArticleDetails(
        @PathVariable("mapType") String mapType, @PathVariable("articleId") Long articleId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ArticlePageResponse response = findArticleDetails(MapType.from(mapType), articleId,
            userDetails);

        return ResponseEntity.ok().body(response);
    }

    private ArticlePageResponse findArticleDetails(MapType mapType, Long articleId,
        CustomUserDetails userDetails) {
        if (mapType == MapType.PUBLIC) {
            return findPublicArticleDetails.findDetails(articleId, userDetails);
        }
        if (mapType == MapType.PRIVATE) {
            return findPrivateArticleDetails.findDetails(articleId, userDetails);
        }
        if (mapType == MapType.GROUPED) {
            return findGroupedArticleDetails.findDetails(articleId, userDetails);
        }
        return null;
    }
}