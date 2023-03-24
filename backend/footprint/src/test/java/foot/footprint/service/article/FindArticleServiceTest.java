package foot.footprint.service.article;

import foot.footprint.domain.article.application.FindArticleService;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleMapResponse;
import foot.footprint.domain.article.dao.FindArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindArticleServiceTest {

    @Mock
    private FindArticleRepository findArticleRepository;

    @Spy
    @InjectMocks
    private FindArticleService findArticleService;

    @Test
    @DisplayName("전체지도 내 게시글 찾기 테스트")
    public void findArticleServiceTest(
    ) {
        //given
        Article article = Article.builder()
                .id(1L)
                .content("ddddd")
                .latitude(10.1)
                .longitude(10.1)
                .private_map(true)
                .public_map(true)
                .title("히히히히")
                .user_id(1L).build();
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        given(findArticleRepository.findArticles(1L, 20.0, 0.0, 20.0, 0.0))
                .willReturn(articles);

        //when
        List<ArticleMapResponse> responses = findArticleService.findMapArticles(1L, 10.0, 10.0, 10.0, 10.0);

        //then
        verify(findArticleRepository).findArticles(1L, 20.0, 0.0, 20.0, 0.0);
        verify(findArticleService).findMapArticles(1L, 10.0, 10.0, 10.0, 10.0);
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getTitle()).isEqualTo(article.getTitle());
    }
}
