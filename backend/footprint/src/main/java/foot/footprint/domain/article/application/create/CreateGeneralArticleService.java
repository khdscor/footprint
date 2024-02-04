package foot.footprint.domain.article.application.create;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.CreateArticleCommand;
import foot.footprint.domain.article.exception.NotIncludedMapException;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateGeneralArticleService implements CreateArticleService {

    private final CreateArticleRepository articleRepository;

    private final ArticleGroupRepository articleGroupRepository;

    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public Long create(CreateArticleCommand command) {
        Article article = Article.createArticle(
                command.getTitle(), command.getContent(), command.getLatitude(), command.getLongitude(),
                command.isPublicMap(), command.isPrivateMap(), command.getMemberId());
        validateMapType(command);
        articleRepository.saveArticle(article);
        if (!command.getGroupIdsToBeIncluded().isEmpty()) {
            articleGroupRepository.saveArticleGroupList(
                    createArticleGroupList(command, article.getId(), command.getMemberId()));
        }
        return article.getId();
    }

    private void validateMapType(CreateArticleCommand command) {
        List<Long> groups = command.getGroupIdsToBeIncluded();
        if (!command.isPublicMap() && !command.isPrivateMap()
                && (Objects.isNull(groups) || groups.isEmpty())) {
            throw new NotIncludedMapException("전체지도, 그룹지도, 개인지도 중 하나는 포함해야합니다.");
        }
    }

    private List<ArticleGroup> createArticleGroupList(CreateArticleCommand command, Long articleId, Long memberId) {
        List<Long> myGroupIds = groupRepository.findAllByMemberId(memberId);
        List<ArticleGroup> articleGroupList = new ArrayList<>();
        for (Long groupId : command.getGroupIdsToBeIncluded()) {
            validateInMyGroup(myGroupIds, groupId);
            articleGroupList.add(ArticleGroup.createArticleGroup(groupId, articleId));
        }
        return articleGroupList;
    }

    private void validateInMyGroup(List<Long> myGroupIds, Long groupId) {
        if (!myGroupIds.contains(groupId)) {
            throw new NotIncludedMapException("그룹 목록에 가입되어있지 않는 그룹이 포함되어 있습니다.");
        }
    }
}