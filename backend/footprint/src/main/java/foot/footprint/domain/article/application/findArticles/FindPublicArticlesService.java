package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articles.ArticleMapResponse;
import foot.footprint.domain.article.dto.articles.ArticleMapCommand;
import foot.footprint.domain.article.dto.articles.PublicArticleMapCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
@RequiredArgsConstructor
public class FindPublicArticlesService implements FindArticlesService {

    private final FindArticleRepository findArticleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(ArticleMapCommand command) {
        PublicArticleMapCommand publicCommand = (PublicArticleMapCommand) command;
        List<Article> articles = findArticleRepository.findPublicArticles(
            publicCommand.getLocationRange());
        return ArticleMapResponse.toResponses(articles);
    }
}