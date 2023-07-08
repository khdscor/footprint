package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.application.findArticleDetails.FindPublicArticleDetails;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleDetailsDto;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.security.user.CustomUserDetails;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindArticleDetailsServiceTest {

    @Mock
    private FindArticleRepository findArticleRepository;

    @Mock
    private ArticleLikeRepository articleLikeRepository;

    @Mock
    private FindCommentRepository findCommentRepository;

    @Mock
    private CommentLikeRepository commentLikeRepository;


    @InjectMocks
    private FindPublicArticleDetails findPublicArticleDetails;

    @BeforeEach
    void setUp() {
        Long memberId = 4L;
        Long articleId = 10L;
        //게시글 리턴
        Article article = buildArticle(memberId, true, true);
        given(findArticleRepository.findById(any())).willReturn(Optional.ofNullable(article));
        //게시글 dto 리턴
        ArticleDetailsDto details = new ArticleDetailsDto(articleId, "title", "content", 1.0, 1.0,
            memberId, "nickName", "image", new Date(), 0L);
        given(findArticleRepository.findArticleDetails(any())).willReturn(details);
        //댓글 리스트 리턴
        CommentResponse response = new CommentResponse(1L, "test", memberId, "nickName", "image",
            new Date(), 0L);
        List<CommentResponse> responses = new ArrayList<>();
        responses.add(response);
        given(findCommentRepository.findAllByArticleId(any())).willReturn(responses);
        //게시글 좋아요 검증 리턴
        given(articleLikeRepository.existsMyLike(any()))
            .willReturn(true);
        //댓글 좋아요 리스트 리턴
        List<Long> commentLikes = new ArrayList<>();
        commentLikes.add(1L);
        given(commentLikeRepository.findCommentIdsILiked(any(), any())).willReturn(
            new ArrayList<>(commentLikes));
    }

    @Test
    @DisplayName("게시글 조회 - 전체공개시")
    public void findDetails() {
        //given
        Long memberId = 4L;
        String email = "email@test.com";
        Long articleId = 10L;
        CustomUserDetails userDetails = new CustomUserDetails(memberId, email, null);

        //when
        ArticlePageResponse response = findPublicArticleDetails.findDetails(articleId,
            userDetails);

        //then
        assertThat(response.getArticleDetails().getId()).isEqualTo(articleId);
        assertThat(response.getComments().size()).isEqualTo(1);
        assertThat(response.getCommentLikes().get(0)).isEqualTo(1L);
        assertThat(response.isArticleLike()).isTrue();
        assertThat(response.getMyMemberId()).isEqualTo(memberId);

        //when 유저 정보가 null(로그인하지 않는 상태)
        ArticlePageResponse response2 = findPublicArticleDetails.findDetails(articleId, null);

        //then
        assertThat(response2.getArticleDetails().getId()).isEqualTo(articleId);
        assertThat(response2.getComments().size()).isEqualTo(1);
        assertThat(response2.getCommentLikes()).isNull();
        assertThat(response2.isArticleLike()).isFalse();
        assertThat(response2.getMyMemberId()).isNull();
    }

    private Article buildArticle(Long memberId, boolean publicMap, boolean privateMap) {
        return Article.builder()
            .content("test")
            .latitude(10.0)
            .longitude(10.0)
            .public_map(publicMap)
            .private_map(privateMap)
            .title("test")
            .create_date(new Date())
            .member_id(memberId).build();
    }
}