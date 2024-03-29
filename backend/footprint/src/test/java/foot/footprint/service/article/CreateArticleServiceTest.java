package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.article.application.create.CreateGeneralArticleService;
import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.CreateArticleCommand;
import foot.footprint.domain.article.exception.NotIncludedMapException;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateArticleServiceTest {

    @Mock
    private CreateArticleRepository createArticleRepository;

    @Mock
    private ArticleGroupRepository articleGroupRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private CreateGeneralArticleService createGeneralArticleService;

    @Test
    @DisplayName("게시글 생성시")
    public void create() {
        //given
        Long memberId = 1L;
        Long groupId1 = 1L;
        Long groupId2 = 2L;
        Set<Long> groupIdsToBeIncluded = new HashSet<>();
        groupIdsToBeIncluded.add(groupId1);
        groupIdsToBeIncluded.add(groupId2);

        ArgumentCaptor<Article> captor = ArgumentCaptor.forClass(Article.class);
        ArgumentCaptor<List<ArticleGroup>> captor2 = ArgumentCaptor.forClass(java.util.List.class);
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("title", "content", memberId,
            10.0, 10.0, true, true, groupIdsToBeIncluded);

        given(createArticleRepository.saveArticle(any()))
            .willReturn(1L);
        given(articleGroupRepository.saveArticleGroupList(anyList()))
            .willReturn(0);
        given(groupRepository.findAllByMemberId(any()))
            .willReturn(groupIdsToBeIncluded);
        //when
        createGeneralArticleService.create(createArticleCommand);

        //then
        verify(createArticleRepository, times(1)).saveArticle(captor.capture());
        verify(articleGroupRepository, times(1)).saveArticleGroupList(captor2.capture());
        Article article = captor.getValue();
        List<ArticleGroup> articleGroups = captor2.getValue();
        assertThat(createArticleCommand.getTitle()).isEqualTo(article.getTitle());
        assertThat(createArticleCommand.getContent()).isEqualTo(article.getContent());
        assertThat(createArticleCommand.getGroupIdsToBeIncluded().size()).isEqualTo(
            articleGroups.size());
        assertThat(articleGroups.get(0).getArticle_id()).isEqualTo(article.getId());

        //groupIds가 중 내 그룹이 아닌 groupId가 있을 시 예외 처리
        //given
        Set<Long> groupIds = new HashSet<>();
        groupIds.add(groupId1);
        groupIds.add(100L);
        createArticleCommand.updateGroupIdList(groupIds);

        //when & then
        assertThatThrownBy(
            () -> createGeneralArticleService.create(createArticleCommand))
            .isInstanceOf(NotIncludedMapException.class);
    }

    @Test
    @DisplayName("게시글 생성시 = 맵 타입 에러")
    public void create_IfNotExistsMapType() {
        //given
        Long memberId = 1L;
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("title", "content", memberId,
            10.0, 10.0, false, false, new HashSet<>());

        //when & then
        assertThatThrownBy(
            () -> createGeneralArticleService.create(createArticleCommand))
            .isInstanceOf(NotIncludedMapException.class);
    }
}