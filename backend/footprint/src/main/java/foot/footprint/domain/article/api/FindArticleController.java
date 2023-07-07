package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.FindArticleDetailsService;
import foot.footprint.domain.article.application.findArticles.FindArticlesService;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.article.dto.ArticleRangeRequest;
import foot.footprint.global.error.exception.WrongInputException;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class FindArticleController {

    private final FindArticlesService findPublicAndPrivateArticles;

    private final FindArticlesService findGroupedArticles;

    private final FindArticleDetailsService findArticleDetailsService;

    public FindArticleController(
        @Qualifier("publicAndPrivate") FindArticlesService findPublicAndPrivateArticles,
        @Qualifier("grouped") FindArticlesService findGroupedArticles,
        FindArticleDetailsService findArticleDetailsService) {
        this.findPublicAndPrivateArticles = findPublicAndPrivateArticles;
        this.findGroupedArticles = findGroupedArticles;
        this.findArticleDetailsService = findArticleDetailsService;
    }

    @GetMapping("/public")
    public ResponseEntity<List<ArticleMapResponse>> findPublicMapArticles(
        ArticleRangeRequest request) {
        validateLocation(request);
        LocationRange locationRange = new LocationRange(request);
        List<ArticleMapResponse> publicMapArticles = findPublicAndPrivateArticles.findArticles(
            null, null, locationRange);
        return ResponseEntity.ok().body(publicMapArticles);
    }

    @GetMapping("/private")
    public ResponseEntity<List<ArticleMapResponse>> findPrivateMapArticles(
        ArticleRangeRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        validateLocation(request);
        LocationRange locationRange = new LocationRange(request);
        List<ArticleMapResponse> privateMapArticles = findPublicAndPrivateArticles.findArticles(
            userDetails.getId(), null, locationRange);
        return ResponseEntity.ok().body(privateMapArticles);
    }

    @GetMapping("/grouped")
    public ResponseEntity<List<ArticleMapResponse>> findGroupedMapArticles(
        @RequestParam(value = "groupId") Long groupId, ArticleRangeRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        validateLocation(request);
        LocationRange locationRange = new LocationRange(request);
        List<ArticleMapResponse> groupedArticles = findGroupedArticles.findArticles(
            userDetails.getId(), groupId, locationRange);
        return ResponseEntity.ok().body(groupedArticles);
    }

    private void validateLocation(ArticleRangeRequest request) {
        if (request.getLatitude() >= 180 || request.getLatitude() <= -180
            || request.getLongitude() >= 180 || request.getLongitude() <= -180) {
            throw new WrongInputException("위치 좌표가 범위를 넘었습니다.");
        }
    }

    @GetMapping("/details/{articleId}")
    public ResponseEntity<ArticlePageResponse> findArticleDetails(
        @PathVariable("articleId") Long articleId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ArticlePageResponse response = findArticleDetailsService.findDetails(articleId,
            userDetails);

        return ResponseEntity.ok().body(response);
    }
}