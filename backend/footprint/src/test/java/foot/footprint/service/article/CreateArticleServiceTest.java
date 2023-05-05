package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.article.application.CreateArticleService;
import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.CreateArticleRequest;
import foot.footprint.domain.article.exception.NotIncludedMapException;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;
import java.util.ArrayList;
import java.util.List;
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

  @InjectMocks
  private CreateArticleService createArticleService;

  @Test
  @DisplayName("게시글 생성시")
  public void create() {
    //given
    Long memberId = 1L;
    Long groupId1 = 1L;
    Long groupId2 = 2L;
    List<Long> groupIdsToBeIncluded = new ArrayList<>();
    groupIdsToBeIncluded.add(groupId1);
    groupIdsToBeIncluded.add(groupId2);

    ArgumentCaptor<Article> captor = ArgumentCaptor.forClass(Article.class);
    ArgumentCaptor<List<ArticleGroup>> captor2 = ArgumentCaptor.forClass(java.util.List.class);
    CreateArticleRequest createArticleRequest = new CreateArticleRequest("title", "content",
        10.0, 10.0, true, true, groupIdsToBeIncluded);

    given(createArticleRepository.saveArticle(any()))
        .willReturn(1L);
    given(articleGroupRepository.saveArticleGroupList(anyList()))
        .willReturn(0);
    //when
    createArticleService.create(createArticleRequest, memberId);

    //then
    verify(createArticleRepository, times(1)).saveArticle(captor.capture());
    verify(articleGroupRepository, times(1)).saveArticleGroupList(captor2.capture());
    Article article = captor.getValue();
    List<ArticleGroup> articleGroups = captor2.getValue();
    assertThat(createArticleRequest.getTitle()).isEqualTo(article.getTitle());
    assertThat(createArticleRequest.getContent()).isEqualTo(article.getContent());
    assertThat(createArticleRequest.getGroupIdsToBeIncluded().size()).isEqualTo(articleGroups.size());
    assertThat(articleGroups.get(0).getArticle_id()).isEqualTo(article.getId());
  }

  @Test
  @DisplayName("게시글 생성시 = 맵 타입 에러")
  public void create_IfNotExistsMapType() {
    //given
    Long memberId = 1L;
    CreateArticleRequest createArticleRequest = new CreateArticleRequest("title", "content",
        10.0, 10.0, false, false, new ArrayList<>());

    //when & then
    assertThatThrownBy(
        () -> createArticleService.create(createArticleRequest, memberId))
        .isInstanceOf(NotIncludedMapException.class);
  }
}