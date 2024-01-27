package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.findArticles.FindArticlesService;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.articles.ArticleMapResponse;
import foot.footprint.domain.article.dto.articles.ArticleRangeRequest;
import foot.footprint.domain.article.dto.articles.GroupedArticleMapCommand;
import foot.footprint.domain.article.dto.articles.PrivateArticleMapCommand;
import foot.footprint.domain.article.dto.articles.PublicArticleMapCommand;
import foot.footprint.global.error.exception.WrongInputException;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class FindArticlesController {

    private final FindArticlesService findPublicArticles;

    private final FindArticlesService findPrivateArticles;

    private final FindArticlesService findGroupedArticles;

    public FindArticlesController(
        @Qualifier("public") FindArticlesService findPublicArticles,
        @Qualifier("private") FindArticlesService findPrivateArticles,
        @Qualifier("grouped") FindArticlesService findGroupedArticles) {
        this.findPublicArticles = findPublicArticles;
        this.findPrivateArticles = findPrivateArticles;
        this.findGroupedArticles = findGroupedArticles;
    }

    @GetMapping("/public")
    public ResponseEntity<List<ArticleMapResponse>> findPublicMapArticles(
        ArticleRangeRequest request) {
        validateLocation(request);
        PublicArticleMapCommand command = new PublicArticleMapCommand(new LocationRange(request));
        List<ArticleMapResponse> publicMapArticles = findPublicArticles.findArticles(command);
        return ResponseEntity.ok().body(publicMapArticles);
    }

    @GetMapping("/private")
    public ResponseEntity<List<ArticleMapResponse>> findPrivateMapArticles(
        ArticleRangeRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        validateLocation(request);
        PrivateArticleMapCommand command = new PrivateArticleMapCommand(
            new LocationRange(request), userDetails.getId());
        List<ArticleMapResponse> privateMapArticles = findPrivateArticles.findArticles(command);
        return ResponseEntity.ok().body(privateMapArticles);
    }

    @GetMapping("/grouped")
    public ResponseEntity<List<ArticleMapResponse>> findGroupedMapArticles(
        @RequestParam(value = "groupId") Long groupId, ArticleRangeRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        validateLocation(request);
        GroupedArticleMapCommand command = new GroupedArticleMapCommand(
            new LocationRange(request), userDetails.getId(), groupId);
        List<ArticleMapResponse> groupedArticles = findGroupedArticles.findArticles(command);
        return ResponseEntity.ok().body(groupedArticles);
    }

    private void validateLocation(ArticleRangeRequest request) {
        if (request.getLatitude() >= 180 || request.getLatitude() <= -180
            || request.getLongitude() >= 180 || request.getLongitude() <= -180) {
            throw new WrongInputException("위치 좌표가 범위를 넘었습니다.");
        }
    }
}