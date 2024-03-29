package foot.footprint.service.articleLike;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.articleLike.application.ChangeGroupedArticleLikeService;
import foot.footprint.domain.articleLike.application.ChangePrivateArticleLikeService;
import foot.footprint.domain.articleLike.application.ChangePublicArticleLikeService;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ObjectSerializer;
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
public class ChangeArticleLikeServiceTest {

    @Spy
    private ArticleLikeRepository articleLikeRepository;

    @Mock
    private FindArticleRepository findArticleRepository;

    @Mock
    private ArticleGroupRepository articleGroupRepository;

    @Mock
    private ObjectSerializer objectSerializer;

    @Spy
    @InjectMocks
    private ChangePublicArticleLikeService publicArticleLikeService;

    @Spy
    @InjectMocks
    private ChangePrivateArticleLikeService privateArticleLikeService;

    @Spy
    @InjectMocks
    private ChangeGroupedArticleLikeService groupedArticleLikeService;

    @Test
    @DisplayName("전체 확인 가능 게시글 좋아요 변화")
    public void changeArticleLike_IfPublicArticle() {
        //given
        Long articleId = 1L;
        Long memberId = 1L;
        Article article = buildArticle(memberId, true, true);
        ArticleLikeCommand likedArticle = new ArticleLikeCommand(articleId, memberId, true);
        ArticleLikeCommand notLikedArticle = new ArticleLikeCommand(articleId, memberId, false);
        given(articleLikeRepository.deleteArticleLike(any(), any()))
            .willReturn(1);
        given(findArticleRepository.findById(any()))
            .willReturn(Optional.ofNullable(article));

        //when
        publicArticleLikeService.changeArticleLike(likedArticle);

        //then
        verify(articleLikeRepository, times(1)).deleteArticleLike(any(), any());
        verify(articleLikeRepository, times(0)).saveArticleLike(any());

        //when
        publicArticleLikeService.changeArticleLike(notLikedArticle);

        //then
        verify(articleLikeRepository, times(1)).saveArticleLike(any());

        //given
        given(articleLikeRepository.deleteArticleLike(any(), any()))
            .willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> publicArticleLikeService.changeArticleLike(likedArticle))
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
        Article article = buildArticle(memberId, false, true);
        ArticleLikeCommand articleLikeCommand = new ArticleLikeCommand(articleId, anotherMemberId);
        given(findArticleRepository.findById(any()))
            .willReturn(Optional.ofNullable(article));

        //when & then
        assertThatThrownBy(
            () -> privateArticleLikeService.changeArticleLike(articleLikeCommand))
            .isInstanceOf(NotMatchMemberException.class);
    }

    @Test
    @DisplayName("그룹 확인 가능 게시글 좋아요 변화")
    public void changeArticleLike_IfGroupedArticle() {
        //changeLike에 대한 테스트는 publicArticle에서 진행하였으므로 validate만 진행
        //given
        Long articleId = 1L;
        Long memberId = 1L;
        Long anotherMemberId = 2L;
        Article article = buildArticle(memberId, false, false);
        ArticleLikeCommand articleLikeCommand = new ArticleLikeCommand(articleId, anotherMemberId);
        given(findArticleRepository.findById(any()))
            .willReturn(Optional.ofNullable(article));
        given(articleGroupRepository.existsArticleInMyGroup(any(), any()))
            .willReturn(false);

        //when & then
        assertThatThrownBy(
            () -> groupedArticleLikeService.changeArticleLike(articleLikeCommand))
            .isInstanceOf(NotAuthorizedOrExistException.class);
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