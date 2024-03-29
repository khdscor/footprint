package foot.footprint.service.article;

import foot.footprint.domain.article.application.findArticles.FindGroupedArticlesService;
import foot.footprint.domain.article.application.findArticles.FindPublicArticlesService;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.articles.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.articles.ArticleRangeRequest;
import foot.footprint.domain.article.dto.articles.GroupedArticleMapCommand;
import foot.footprint.domain.article.dto.articles.PublicArticleMapCommand;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindArticlesServiceTest {

    @Mock
    private FindArticleRepository findArticleRepository;

    @Mock
    private MemberGroupRepository memberGroupRepository;

    @Spy
    @InjectMocks
    private FindPublicArticlesService findPublicArticlesService;

    @Spy
    @InjectMocks
    private FindGroupedArticlesService findGroupedArticlesService;

    @Test
    @DisplayName("전체지도 내 게시글 찾기 테스트")
    public void findPublicMapArticlesTest() {
        //given
        List<Article> articles = createArticleList(createArticle());
        LocationRange locationRange = new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0));
        PublicArticleMapCommand command = new PublicArticleMapCommand(locationRange);
        given(findArticleRepository.findPublicArticles(locationRange)).willReturn(articles);

        //when
        List<ArticleMapResponse> responses = findPublicArticlesService.findArticles(command);

        //then
        verify(findArticleRepository, times(1)).findPublicArticles(locationRange);
        verify(findPublicArticlesService, times(1)).findArticles(command);
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("그룹지도")
    public void groupedMapArticlesTest() {
        //given
        List<Article> articles = createArticleList(createArticle());
        LocationRange locationRange = new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0));
        GroupedArticleMapCommand command = new GroupedArticleMapCommand(locationRange, 1L, 1L);
        given(findArticleRepository.findArticlesByGroup(1L, locationRange)).willReturn(articles);
        given(memberGroupRepository.existsMemberInGroup(anyLong(), anyLong())).willReturn(true);

        //when
        List<ArticleMapResponse> responses = findGroupedArticlesService.findArticles(command);

        //then
        assertThat(responses.size()).isEqualTo(1);

        // member가 group에 속하지 않을 시
        //given
        given(memberGroupRepository.existsMemberInGroup(anyLong(), anyLong())).willReturn(false);

        // when & then
        assertThatThrownBy(
            () -> findGroupedArticlesService.findArticles(command)).isInstanceOf(
            NotAuthorizedOrExistException.class);
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