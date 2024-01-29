package foot.footprint.service.comment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.application.edit.EditGeneralCommentService;
import foot.footprint.domain.comment.dao.EditCommentRepository;
import foot.footprint.domain.comment.dto.EditCommentCommand;
import foot.footprint.global.util.ObjectSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EditCommentServiceTest {

    @Mock
    private EditCommentRepository editCommentRepository;

    @Mock
    private ObjectSerializer objectSerializer;

    @InjectMocks
    private EditGeneralCommentService editGeneralCommentService;

    @Test
    void edit() {
        //given
        given(editCommentRepository.editComment(any(), any(), any()))
            .willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> editGeneralCommentService.edit(new EditCommentCommand(1L, 1L, 1L, "test")))
            .isInstanceOf(NotMatchMemberException.class);
    }
}