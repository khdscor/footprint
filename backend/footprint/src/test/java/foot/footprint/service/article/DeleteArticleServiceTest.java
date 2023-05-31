package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.application.DeleteArticleService;
import foot.footprint.domain.article.dao.DeleteArticleRepository;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteArticleServiceTest {

  @Mock
  private DeleteArticleRepository deleteArticleRepository;

  @InjectMocks
  private DeleteArticleService deleteArticleService;

  @Test
  public void edit() {
    //given
    given(deleteArticleRepository.deleteById(any(), any()))
        .willReturn(0);

    //when & then
    assertThatThrownBy(
        () -> deleteArticleService.delete(any(), any()))
        .isInstanceOf(NotMatchMemberException.class);
  }
}
