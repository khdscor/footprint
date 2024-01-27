package foot.footprint.domain.article.application.findArticles;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articles.ArticleMapResponse;
import foot.footprint.domain.article.dto.articles.ArticleMapCommand;
import foot.footprint.domain.article.dto.articles.GroupedArticleMapCommand;
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
public class FindGroupedArticlesService implements FindArticlesService {

    private final FindArticleRepository findArticleRepository;

    private final MemberGroupRepository memberGroupRepository;


    @Override
    @Transactional(readOnly = true)
    public List<ArticleMapResponse> findArticles(ArticleMapCommand command) {
        GroupedArticleMapCommand groupedCommand = (GroupedArticleMapCommand) command;
        validateMemberInGroup(groupedCommand.getMemberId(), groupedCommand.getGroupId());
        List<Article> articles = findArticleRepository.findArticlesByGroup(
            groupedCommand.getGroupId(), groupedCommand.getLocationRange());
        return ArticleMapResponse.toResponses(articles);
    }

    private void validateMemberInGroup(Long memberId, Long groupId) {
        if (!memberGroupRepository.existsMemberInGroup(memberId, groupId)) {
            throw new NotAuthorizedOrExistException("해당 그룹에 접근할 권한이 없습니다.");
        }
    }
}