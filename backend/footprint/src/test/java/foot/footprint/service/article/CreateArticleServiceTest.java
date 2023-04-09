package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.article.application.CreateArticleService;
import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.CreateArticleRequest;
import foot.footprint.domain.article.exception.NotIncludedMapException;
import java.util.ArrayList;
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

  @InjectMocks
  private CreateArticleService createArticleService;

  @Test
  @DisplayName("게시글 생성시")
  public void create() {
    //given
    Long memberId = 1L;
    ArgumentCaptor<Article> captor = ArgumentCaptor.forClass(Article.class);
    CreateArticleRequest createArticleRequest = new CreateArticleRequest("title", "content",
        10.0, 10.0, true, true, new ArrayList<>());

    //when
    createArticleService.create(createArticleRequest, memberId);

    //then
    verify(createArticleRepository, times(1)).saveArticle(captor.capture());
    Article article = captor.getValue();
    assertThat(createArticleRequest.getTitle()).isEqualTo(article.getTitle());
    assertThat(createArticleRequest.getContent()).isEqualTo(article.getContent());
  }

  @Test
  @DisplayName("게시글 생성시 = 맵 타입 에러")
  public void create_IfNotExistsMapType() {
    //given
    Long memberId = 1L;
    CreateArticleRequest createArticleRequest = new CreateArticleRequest("title", "content",
        10.0, 10.0, false, false, new ArrayList<>());

    assertThatThrownBy(
        () -> createArticleService.create(createArticleRequest, memberId))
        .isInstanceOf(NotIncludedMapException.class);
  }
}