package foot.footprint.domain.article.application;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindArticleService {

    private final FindArticleRepository findArticleRepository;

    private final MemberGroupRepository memberGroupRepository;

    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findPublicMapArticles(
        LocationRange locationRange) {
        Long memberId = null;
        return ArticleMapResponse.toResponses(findArticles(memberId, locationRange));
    }

    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findPrivateMapArticles(
        Long memberId, LocationRange locationRange) {
        return ArticleMapResponse.toResponses(findArticles(memberId, locationRange));
    }

    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findGroupedArticles(
        Long memberId, Long groupId, LocationRange locationRange) {
        validateHasJoined(groupId, memberId);
        List<Article> articles = findArticleRepository.findArticlesByGroup(groupId, locationRange);
        return ArticleMapResponse.toResponses(articles);
    }

    private List<Article> findArticles(Long memberId, LocationRange locationRange) {
        return findArticleRepository.findArticles(memberId, locationRange);
    }

    private void validateHasJoined(Long groupId, Long memberId) {
        boolean isPresent = memberGroupRepository.checkAlreadyJoined(groupId, memberId);
        if (!isPresent) {
            throw new NotExistsException("그룹에 속해있지 않습니다.");
        }
    }
}