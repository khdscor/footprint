package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier("private")
@RequiredArgsConstructor
public class FindPrivateArticles implements FindArticlesService {

    private final FindArticleRepository findArticleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(Long memberId, Long groupId,
        LocationRange locationRange) {
        List<Article> articles = findArticleRepository.findPrivateArticles(memberId, locationRange);
        return ArticleMapResponse.toResponses(articles);
    }
}