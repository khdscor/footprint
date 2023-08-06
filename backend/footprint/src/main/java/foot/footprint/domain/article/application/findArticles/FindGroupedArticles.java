package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("grouped")
@RequiredArgsConstructor
public class FindGroupedArticles implements FindArticlesService {

    private final FindArticleRepository findArticleRepository;

    private final MemberGroupRepository memberGroupRepository;


    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(Long memberId, Long groupId,
        LocationRange locationRange) {
        validateMemberInGroup(memberId, groupId);
        List<Article> articles = findArticleRepository.findArticlesByGroup(groupId, locationRange);
        return ArticleMapResponse.toResponses(articles);
    }

    private void validateMemberInGroup(Long memberId, Long groupId){
        if(!memberGroupRepository.existsMemberInGroup(memberId, groupId)){
            throw new NotAuthorizedOrExistException("해당 그룹에 접근할 권한이 없습니다.");
        }
    }
}