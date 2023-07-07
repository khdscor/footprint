package foot.footprint.domain.article.application.findArticle;

import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import java.util.List;

public interface FindArticleService {

    public List<ArticleMapResponse> findArticles(Long memberId, Long groupId,
        LocationRange locationRange);
}