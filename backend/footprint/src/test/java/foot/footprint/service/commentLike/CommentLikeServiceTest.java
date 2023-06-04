package foot.footprint.service.commentLike;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.commentLike.application.CommentLikeService;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
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
public class CommentLikeServiceTest {

  @Mock
  private FindArticleRepository findArticleRepository;

  @Spy
  private CommentLikeRepository commentLikeRepository;

  @Mock
  private ArticleGroupRepository articleGroupRepository;

  @InjectMocks
  private CommentLikeService commentLikeService;

  @Test
  @DisplayName("댓글 좋아요 변화 - 전체지도 시")
  public void changeMyLike() {
    //given 좋아요를 안했을 시
    Long memberId = 1L;
    given(findArticleRepository.findById(any())).willReturn(
        Optional.ofNullable(createArticle(memberId, false, true)));
    given(commentLikeRepository.saveCommentLike(any())).willReturn(1);

    //when
    commentLikeService.changeMyLike(1L, 1L, false, 1L);

    //then
    verify(commentLikeRepository, times(1)).saveCommentLike(any());
    verify(commentLikeRepository, times(0)).deleteCommentLike(any(), any());

    //given 이미 좋아요 했을 시
    given(commentLikeRepository.deleteCommentLike(any(), any())).willReturn(1);

    //when
    commentLikeService.changeMyLike(1L, 1L, true, 1L);

    //then
    verify(commentLikeRepository, times(1)).deleteCommentLike(any(), any());

    //given 이미 좋아요 했을 시, 예외 발생시
    given(commentLikeRepository.deleteCommentLike(any(), any())).willReturn(0);

    //when & then
    assertThatThrownBy(
        () -> commentLikeService.changeMyLike(1L, 1L, true, 1L))
        .isInstanceOf(NotExistsException.class);
  }

  @Test
  @DisplayName("댓글 좋아요 변화 - 개인지도 시")
  public void changeMyLike_IfPrivateArticle() {
    //given
    Long memberId = 1L;
    given(findArticleRepository.findById(any())).willReturn(
        Optional.ofNullable(createArticle(memberId, true, false)));

    //when & then
    assertThatThrownBy(
        () -> commentLikeService.changeMyLike(1L, 1L, true, 2L))
        .isInstanceOf(NotMatchMemberException.class);
  }

  @Test
  @DisplayName("댓글 좋아요 변화 - 그룹지도 시")
  public void changeMyLike_IfGroupedArticle() {
    //given
    Long memberId = 1L;
    given(findArticleRepository.findById(any())).willReturn(
        Optional.ofNullable(createArticle(memberId, false, false)));
    given(articleGroupRepository.existsArticleInMyGroup(any(), any())).willReturn(false);

    //when & then
    assertThatThrownBy(
        () -> commentLikeService.changeMyLike(1L, 1L, true, 2L))
        .isInstanceOf(NotAuthorizedOrExistException.class);
  }

  private Article createArticle(Long memberId, boolean privateMap, boolean publicMap) {
    return Article.builder().id(1L).content("ddddd").latitude(10.1).longitude(10.1)
        .private_map(privateMap).public_map(publicMap).title("히히히히").member_id(memberId).build();
  }
}