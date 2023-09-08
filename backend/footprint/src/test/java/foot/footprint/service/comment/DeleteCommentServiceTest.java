package foot.footprint.service.comment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.application.delete.DeleteGeneralCommentService;
import foot.footprint.domain.comment.dao.DeleteCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteCommentServiceTest {

    @Mock
    private DeleteCommentRepository deleteCommentRepository;

    @InjectMocks
    private DeleteGeneralCommentService deleteGeneralCommentService;

    @Test
    void edit() {
        //given
        given(deleteCommentRepository.deleteComment(any(), any()))
            .willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> deleteGeneralCommentService.delete(any(), any()))
            .isInstanceOf(NotMatchMemberException.class);
    }
}