package foot.footprint.service.articleLike;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.articleLike.application.ArticleLikeService;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.global.domain.MapType;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleLikeServiceTest {

  @Spy
  private ArticleLikeRepository articleLikeRepository;

  @Mock
  private FindArticleRepository findArticleRepository;
  @Spy
  @InjectMocks
  private ArticleLikeService articleLikeService;

  @Test
  @DisplayName("전체 확인 가능 게시글 좋아요 변화")
  public void changeArticleLike_IfPublicArticle() {
    //given
    Long articleId = 1L;
    Long memberId = 1L;
    Article article = buildArticle(memberId);
    ArticleLikeDto likedArticle = new ArticleLikeDto(articleId, memberId, true);
    ArticleLikeDto notLikedArticle = new ArticleLikeDto(articleId, memberId, false);
    given(articleLikeRepository.deleteArticleLike(any()))
        .willReturn(1);
    given(findArticleRepository.findById(any()))
        .willReturn(Optional.ofNullable(article));

    //when
    articleLikeService.changeArticleLike(likedArticle, MapType.PUBLIC);

    //then
    verify(articleLikeRepository, times(1)).deleteArticleLike(any());
    verify(articleLikeRepository, times(0)).saveArticleLike(any());

    //when
    articleLikeService.changeArticleLike(notLikedArticle, MapType.PUBLIC);

    //then
    verify(articleLikeRepository, times(1)).saveArticleLike(any());

    //given
    given(articleLikeRepository.deleteArticleLike(any()))
        .willReturn(0);

    //when & then
    assertThatThrownBy(
        () -> articleLikeService.changeArticleLike(likedArticle, MapType.PUBLIC))
        .isInstanceOf(NotExistsException.class);
  }

  @Test
  @DisplayName("개인 확인 가능 게시글 좋아요 변화")
  public void changeArticleLike_IfPrivateArticle() {
    //changeLike에 대한 테스트는 publicArticle에서 진행하였으므로 validate만 진행
    //given
    Long articleId = 1L;
    Long memberId = 1L;
    Long anotherMemberId = 2L;
    Article article = buildArticle(memberId);
    ArticleLikeDto articleLikeDto = new ArticleLikeDto(articleId, anotherMemberId);
    given(findArticleRepository.findById(any()))
        .willReturn(Optional.ofNullable(article));

    //when & then
    assertThatThrownBy(
        () -> articleLikeService.changeArticleLike(articleLikeDto, MapType.PRIVATE))
        .isInstanceOf(NotMatchMemberException.class);
  }

  private Article buildArticle(Long memberId) {
    return Article.builder()
        .content("test")
        .latitude(10.0)
        .longitude(10.0)
        .public_map(true)
        .private_map(true)
        .title("test")
        .create_date(new Date())
        .member_id(memberId).build();
  }
}