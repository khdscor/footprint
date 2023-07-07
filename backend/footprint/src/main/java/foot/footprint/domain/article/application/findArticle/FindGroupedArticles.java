package foot.footprint.domain.article.application.findArticle;

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
@Qualifier("grouped")
@RequiredArgsConstructor
public class FindGroupedArticles implements FindArticleService {

    private final FindArticleRepository findArticleRepository;


    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(Long memberId, Long groupId,
        LocationRange locationRange) {
        List<Article> articles = findArticleRepository.findArticlesByGroup(groupId, locationRange);
        return ArticleMapResponse.toResponses(articles);
    }
}