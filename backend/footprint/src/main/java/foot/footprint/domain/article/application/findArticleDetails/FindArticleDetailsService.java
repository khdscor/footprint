package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.global.security.user.CustomUserDetails;

public interface FindArticleDetailsService {
    ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails);
}