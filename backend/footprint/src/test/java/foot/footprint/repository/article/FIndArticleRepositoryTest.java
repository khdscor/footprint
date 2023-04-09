package foot.footprint.repository.article;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleRangeRequest;
import foot.footprint.domain.article.exception.NotIncludedMapException;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FIndArticleRepositoryTest extends RepositoryTest {

  @Autowired
  private FindArticleRepository findArticleRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void findArticlesTest() {
    //given
    saveArticle(10.0, 10.0, true, false);
    saveArticle(35.0, 125.0, false, true);

    //when
    //publicMapArticles
    List<Article> articlesN = findArticleRepository.findArticles(
        null,
        new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0))
    );
    //publicMapArticles, but private 글
    List<Article> articlesN2 = findArticleRepository.findArticles(
        1L,
        new LocationRange(
            new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0))
    );
    //privateMapArticles 다른 유저
    List<Article> articles1 = findArticleRepository.findArticles(
        1L,
        new LocationRange(
            new ArticleRangeRequest(35.0, 5.0, 125.0, 5.0))
    );
    //privateMapArticles 해당 유저
    List<Article> articles2 = findArticleRepository.findArticles(
        2L,
        new LocationRange(
            new ArticleRangeRequest(35.0, 5.0, 125.0, 5.0))
    );
    //publicMapArticles 전체지도 but privateArticle만 존재
    List<Article> articles3 = findArticleRepository.findArticles(
        null,
        new LocationRange(
            new ArticleRangeRequest(35.0, 5.0, 125.0, 5.0))
    );
    //then
    assertThat(articlesN).hasSize(1);
    assertThat(articlesN2).hasSize(0);
    assertThat(articles1).hasSize(0);
    assertThat(articles2).hasSize(1);
    assertThat(articles3).hasSize(0);
  }

  @Test
  public void findArticle(){
    //given
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);

    //when
    Optional<Article> savedArticle = findArticleRepository.findArticle(article.getId());

    //then
    assertThat(article.getId()).isEqualTo(savedArticle.get().getId());
    assertThat(article.getCreate_date()).isEqualTo(savedArticle.get().getCreate_date());
    assertThat(article.getTitle()).isEqualTo(savedArticle.get().getTitle());
  }

  private void saveArticle(double lat, double lng, boolean publicMap, boolean privateMap) {
    Member member = buildMember();
    memberRepository.saveMember(member);
    System.out.println("memberId: " + member.getId());
    Article article = Article.builder()
        .content("test")
        .latitude(lat)
        .longitude(lng)
        .public_map(publicMap)
        .private_map(privateMap)
        .title("test")
        .create_date(new Date())
        .member_id(member.getId()).build();
    createArticleRepository.saveArticle(article);
  }
}