package foot.footprint.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.application.findArticleDetails.FindPublicArticleDetailsService;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticleDetailsDto;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageDto;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageIfNonLoginDto;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.comment.dto.CommentsDto;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
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
    private CommentLikeRepository commentLikeRepository;

    @Mock
    private ObjectSerializer objectSerializer;

    @InjectMocks
    private FindPublicArticleDetailsService findPublicArticleDetailsService;

    @BeforeEach
    void setUp() {
        Long memberId = 4L;
        Long articleId = 10L;
        //게시글 리턴
        Article article = buildArticle(memberId, true, true);
        //게시글 dto 리턴
        ArticleDetailsDto details = new ArticleDetailsDto(articleId, "title", "content", 1.0, 1.0,
            true, true, memberId, "nickName", "image", new Date(), 0L);
        CommentsDto response = new CommentsDto(1L, "test", memberId, "nickName", "image",
            new Date(), 0L);
        List<CommentsDto> responses = new ArrayList<>();
        responses.add(response);
        ArticlePageDto dto = new ArticlePageDto(articleId, details, true, responses);
        given(findArticleRepository.findArticleDetails(any(), any())).willReturn(Optional.of(dto));
        //댓글 좋아요 리스트 리턴
        List<Long> commentLikes = new ArrayList<>();
        commentLikes.add(1L);
        given(commentLikeRepository.findCommentIdsILiked(any(), any())).willReturn(
            new ArrayList<>(commentLikes));
        //게시글 dto 리턴, 만약 로그인 하지 않았을 시
        ArticlePageIfNonLoginDto nonLoginDto = new ArticlePageIfNonLoginDto(articleId, details,
            responses);
        given(findArticleRepository.findArticleDetailsIfNonLogin(articleId)).willReturn(
            Optional.of(nonLoginDto));
        //objectSerializer - 캐시 데이터는 없다고 가정하고 리턴
        given(objectSerializer.getData(any(), any())).willReturn(Optional.empty());
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
        ArticlePageResponse response = findPublicArticleDetailsService.findDetails(articleId,
            userDetails);

        //then
        assertThat(response.getArticleDetails().getId()).isEqualTo(articleId);
        assertThat(response.getComments().size()).isEqualTo(1);
        assertThat(response.getCommentLikes().get(0)).isEqualTo(1L);
        assertThat(response.isArticleLike()).isTrue();
        assertThat(response.getMyMemberId()).isEqualTo(memberId);

        //when 유저 정보가 null(로그인하지 않는 상태)
        ArticlePageResponse response2 = findPublicArticleDetailsService.findDetails(articleId, null);

        //then
        assertThat(response2.getArticleDetails().getId()).isEqualTo(articleId);
        assertThat(response2.getComments().size()).isEqualTo(1);
        assertThat(response2.getCommentLikes()).isEmpty();
        assertThat(response2.isArticleLike()).isFalse();
        assertThat(response2.getMyMemberId()).isEqualTo(-1L);
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