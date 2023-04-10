package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.application.DeleteArticleService;
import foot.footprint.domain.article.application.EditArticleService;
import foot.footprint.domain.article.dao.DeleteArticleRepository;
import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EditArticleServiceTest {

  @Mock
  private EditArticleRepository editArticleRepository;

  @Spy
  private FindArticleRepository findArticleRepository;

  @InjectMocks
  private EditArticleService editArticleService;

  @Test
  @DisplayName("글 수정시 - 해당하는 글이 존재하지 않을 때")
  public void edit_IfNotExistsArticle() {
    //given
    Long articleId = 1L;
    Long memberId = 1L;
    String newContent = "test";

    //when & then
    assertThatThrownBy(
        () -> editArticleService.edit(articleId, memberId, newContent))
        .isInstanceOf(NotExistsException.class);
  }

  @Test
  @DisplayName("글 수정시 - 작성자와 전달된 member의 id가 다를 때")
  public void delete_IfNotMatchMember() {
    //given
    Long articleId = 1L;
    Long memberId = 1L;
    Long writerId = 2L;
    String newContent = "test";
    Article article = createArticle(articleId, writerId);
    given(findArticleRepository.findById(any()))
        .willReturn(Optional.ofNullable(article));

    //when & then
    editArticleService.edit(articleId, writerId, newContent);
    assertThatThrownBy(
        () -> editArticleService.edit(articleId, memberId, newContent))
        .isInstanceOf(NotMatchMemberException.class);
  }

  private Article createArticle(Long articleId, Long memberId) {
    return Article.builder()
        .id(articleId)
        .content("ddddd")
        .latitude(10.1)
        .longitude(10.1)
        .private_map(true)
        .public_map(true)
        .title("히히히히")
        .member_id(memberId).build();
  }
}