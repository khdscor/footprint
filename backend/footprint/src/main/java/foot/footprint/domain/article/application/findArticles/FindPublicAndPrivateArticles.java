package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("publicAndPrivate")
@RequiredArgsConstructor
public class FindPublicAndPrivateArticles implements FindArticlesService {

    private final FindArticleRepository findArticleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(Long memberId, Long groupId,
        LocationRange locationRange) {
        List<Article> articles = findArticleRepository.findArticles(memberId, locationRange);
        return ArticleMapResponse.toResponses(articles);
    }
}