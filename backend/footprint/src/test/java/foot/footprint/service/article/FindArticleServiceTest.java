package foot.footprint.service.article;

import foot.footprint.domain.article.application.FindArticleService;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.ArticleRangeRequest;
import foot.footprint.domain.article.dto.GroupMapArticlesDto;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindArticleServiceTest {

    @Mock
    private FindArticleRepository findArticleRepository;

    @Mock
    private GroupRepository groupRepository;

    @Spy
    @InjectMocks
    private FindArticleService findArticleService;

    @Test
    @DisplayName("전체지도 내 게시글 찾기 테스트")
    public void findPublicMapArticlesTest() {
        //given
        List<Article> articles = createArticleList(createArticle());
        Long userId = null;
        LocationRange locationRange = new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0));
        given(findArticleRepository.findArticles(userId, locationRange)).willReturn(articles);

        //when
        List<ArticleMapResponse> responses = findArticleService.findPublicMapArticles(
            locationRange);

        //then
        verify(findArticleRepository, times(1)).findArticles(userId, locationRange);
        verify(findArticleService, times(1)).findPublicMapArticles(locationRange);
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("그룹지도")
    public void groupedMapArticlesTest() {
        //given
        String groupName = "테스트입니다.";
        List<Article> articles = createArticleList(createArticle());
        LocationRange locationRange = new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0));
        given(groupRepository.findGroupName(any(), any())).willReturn(Optional.of(groupName));
        given(findArticleRepository.findArticlesByGroup(1L, locationRange)).willReturn(articles);

        //when
        GroupMapArticlesDto result = findArticleService.findGroupedArticles(1L, 1L,
            locationRange);

        //then
        assertThat(result.getResponses().size()).isEqualTo(1);
        assertThat(result.getGroupName()).isEqualTo(groupName);
    }

    @Test
    @DisplayName("그룹지도 - 그룹에 가입된 회원이 아닐 시")
    public void groupedMapArticlesTest_IfNotJoined() {
        //given
        LocationRange locationRange = new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0));

        //when & then
        assertThatThrownBy(
            () -> findArticleService.findGroupedArticles(1L, 1L, locationRange)).isInstanceOf(
            NotExistsException.class);
    }

    private Article createArticle() {
        return Article.builder().id(1L).content("ddddd").latitude(10.1).longitude(10.1)
            .private_map(true).public_map(true).title("히히히히").member_id(1L).build();
    }

    private List<Article> createArticleList(Article article) {
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        return articles;
    }
}