package foot.footprint.service.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.comment.application.find.FindCommentOnPage;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.comment.dto.CommentOnPageResponse;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.featureFactory.CommentFeatureFactory;
import foot.footprint.featureFactory.MemberFeatureFactory;
import foot.footprint.global.domain.AuthorDto;
import java.util.ArrayList;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindCommentServiceTest {

    @Mock
    private FindCommentRepository findCommentRepository;

    @InjectMocks
    private FindCommentOnPage findCommentOnPage;

    @Test
    @DisplayName("커서기반 댓글 페이지 조회 - 다음페이지가 있을 시")
    public void FindCommentInPage() {
        //given
        List<CommentResponse> responses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyRandom commentEasyRandom = CommentFeatureFactory.create((long) i, 1L, 1L);
            Comment comment = commentEasyRandom.nextObject(Comment.class);
            EasyRandom memberEasyRandom = MemberFeatureFactory.create(1L);
            Member member = memberEasyRandom.nextObject(Member.class);
            AuthorDto authorDto = AuthorDto.buildAuthorDto(member);
            responses.add(CommentResponse.toCommentResponse(comment, authorDto));
        }
        given(findCommentRepository.findAllByArticleIdOnPage(any(), any()))
            .willReturn(responses);

        //when
        CommentOnPageResponse response = findCommentOnPage.findComments(1L, 10L);

        //then
        assertThat(response.isHasNextPage()).isTrue();
        assertThat(response.getCursorId()).isEqualTo(responses.get(9).getId());
        assertThat(response.getComments()).hasSize(10);
    }

    @Test
    @DisplayName("커서기반 댓글 페이지 조회 - 다음페이지가 없을 시")
    public void FindCommentInPage_IfCommentsSizeLt10() {
        //given
        List<CommentResponse> responses = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            EasyRandom commentEasyRandom = CommentFeatureFactory.create((long) i, 1L, 1L);
            Comment comment = commentEasyRandom.nextObject(Comment.class);
            EasyRandom memberEasyRandom = MemberFeatureFactory.create(1L);
            Member member = memberEasyRandom.nextObject(Member.class);
            AuthorDto authorDto = AuthorDto.buildAuthorDto(member);
            responses.add(CommentResponse.toCommentResponse(comment, authorDto));
        }
        given(findCommentRepository.findAllByArticleIdOnPage(any(), any()))
            .willReturn(responses);

        //when
        CommentOnPageResponse response = findCommentOnPage.findComments(1L, 6L);

        //then
        assertThat(response.isHasNextPage()).isFalse();
        assertThat(response.getCursorId()).isEqualTo(-1);
        assertThat(response.getComments()).hasSize(6);
    }
}