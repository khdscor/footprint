package foot.footprint.domain.article.api;

import foot.footprint.domain.article.application.DeleteArticleService;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class DeleteArticleController {

    private final DeleteArticleService deleteArticleService;

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        deleteArticleService.delete(articleId, userDetails.getId());

        return ResponseEntity.noContent()
            .build();
    }
}