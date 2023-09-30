package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.global.security.user.CustomUserDetails;

public interface FindArticleDetailsService {
    ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails);
}