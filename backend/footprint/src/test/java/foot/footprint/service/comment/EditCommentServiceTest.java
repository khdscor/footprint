package foot.footprint.service.comment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.application.EditCommentService;
import foot.footprint.domain.comment.dao.EditCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EditCommentServiceTest {

  @Mock
  private EditCommentRepository editCommentRepository;

  @InjectMocks
  private EditCommentService editCommentService;

  @Test
  void edit(){
    //given
    given(editCommentRepository.editComment(any(), any(), any()))
        .willReturn(0);

    //when & then
    assertThatThrownBy(
        () -> editCommentService.edit(any(), any(), any()))
        .isInstanceOf(NotMatchMemberException.class);
  }
}