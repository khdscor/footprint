package foot.footprint.domain.article.application;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.GroupMapArticlesDto;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindArticleService {

    private final FindArticleRepository findArticleRepository;

    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findPublicMapArticles(LocationRange locationRange) {
        Long memberId = null;
        return ArticleMapResponse.toResponses(findArticles(memberId, locationRange));
    }

    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findPrivateMapArticles(Long memberId,
        LocationRange locationRange) {
        return ArticleMapResponse.toResponses(findArticles(memberId, locationRange));
    }

    @Transactional(readOnly = true)
    public GroupMapArticlesDto findGroupedArticles(Long memberId, Long groupId,
        LocationRange locationRange) {
        String groupName = findGroupName(groupId, memberId);
        List<Article> articles = findArticleRepository.findArticlesByGroup(groupId, locationRange);
        return new GroupMapArticlesDto(ArticleMapResponse.toResponses(articles), groupName);
    }

    private List<Article> findArticles(Long memberId, LocationRange locationRange) {
        return findArticleRepository.findArticles(memberId, locationRange);
    }

    private String findGroupName(Long groupId, Long memberId) {
        return groupRepository.findGroupName(memberId, groupId)
            .orElseThrow(() -> new NotExistsException("그룹에 속해있지 않거나 해당되는 그룹이 존재하지 않습니다."));
    }
}