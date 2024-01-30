package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dto.articles.ArticleMapCommand;
import foot.footprint.domain.article.dto.articles.ArticleMapResponse;
import java.util.List;

public interface FindArticlesService {

    List<ArticleMapResponse> findArticles(ArticleMapCommand command);
}