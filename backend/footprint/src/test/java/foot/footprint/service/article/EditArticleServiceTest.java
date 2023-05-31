package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.application.EditArticleService;
import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EditArticleServiceTest {

  @Mock
  private EditArticleRepository editArticleRepository;

  @InjectMocks
  private EditArticleService editArticleService;

  @Test
  public void edit(){
    //given
    given(editArticleRepository.editArticle(any(), any(), any()))
        .willReturn(0);

    //when & then
    assertThatThrownBy(
        () -> editArticleService.edit(any(), any(), any()))
        .isInstanceOf(NotMatchMemberException.class);
  }
}
