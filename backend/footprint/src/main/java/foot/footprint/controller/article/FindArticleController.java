package foot.footprint.controller.article;

import foot.footprint.dto.article.ArticleMapResponse;
import foot.footprint.dto.article.ArticleRangeRequest;
import foot.footprint.service.article.FindArticleService;
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
        Long userId = null; // publicMap 조회
        List<ArticleMapResponse> publicMapArticles = findArticleService.findMapArticles(userId,
                request.getLatitude(), request.getLatitudeRange(), request.getLongitude(), request.getLongitudeRange()
        );
        return ResponseEntity.ok()
                .body(publicMapArticles);
    }
}
