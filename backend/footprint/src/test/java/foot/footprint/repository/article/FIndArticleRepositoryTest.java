package foot.footprint.repository.article;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FIndArticleRepositoryTest extends RepositoryTest {

  @Autowired
  private FindArticleRepository findArticleRepository;

  @Test
  public void findArticlesTest() {
    //when
    //publicMapArticles
    List<Article> articlesN = findArticleRepository.findArticles(
        null,
        20.0,
        0.0,
        20.0,
        0.0
    );
    //publicMapArticles, but pubilc 글
    List<Article> articlesN2 = findArticleRepository.findArticles(
        1L,
        20.0,
        0.0,
        20.0,
        0.0
    );
    //privateMapArticles 유저o
    List<Article> articles1 = findArticleRepository.findArticles(
        1L,
        40.0,
        30.0,
        130.0,
        120.0
    );
    //privateMapArticles 유저x
    List<Article> articles2 = findArticleRepository.findArticles(
        2L,
        40.0,
        30.0,
        130.0,
        120.0
    );
    //publicMapArticles 전체지도 but privateArticle만 존재
    List<Article> articles3 = findArticleRepository.findArticles(
        null,
        40.0,
        30.0,
        130.0,
        120.0
    );
    //then
    assertThat(articlesN).hasSize(1);
    assertThat(articlesN2).hasSize(0);
    assertThat(articles1).hasSize(1);
    assertThat(articles2).hasSize(0);
    assertThat(articles3).hasSize(0);
  }
}