package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articles.ArticleMapResponse;
import foot.footprint.domain.article.dto.articles.ArticleMapCommand;
import foot.footprint.domain.article.dto.articles.PrivateArticleMapCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier("private")
@RequiredArgsConstructor
public class FindPrivateArticlesService implements FindArticlesService {

    private final FindArticleRepository findArticleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(ArticleMapCommand command) {
        PrivateArticleMapCommand privateCommand = (PrivateArticleMapCommand) command;
        List<Article> articles = findArticleRepository.findPrivateArticles(
            privateCommand.getMemberId(), privateCommand.getLocationRange());
        return ArticleMapResponse.toResponses(articles);
    }
}