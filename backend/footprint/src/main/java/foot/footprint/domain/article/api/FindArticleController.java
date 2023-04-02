package foot.footprint.domain.article.api;

import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dto.ArticleRangeRequest;
import foot.footprint.domain.article.application.FindArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class FindArticleController {

    private final FindArticleService findArticleService;

    @GetMapping("/public")
    public ResponseEntity<List<ArticleMapResponse>> findPublicMapArticles(ArticleRangeRequest request) {
        LocationRange locationRange = new LocationRange(request);
        List<ArticleMapResponse> publicMapArticles = findArticleService.findPublicMapArticles(locationRange);
        return ResponseEntity.ok()
                .body(publicMapArticles);
    }
}