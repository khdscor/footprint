package foot.footprint.repository.article;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleRangeRequest;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    saveArticle(10.0, 10.0, true,false);
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
    System.out.println("articleId: " + article.getId());
  }

  private Member buildMember() {
    return Member.builder()
        .nick_name("nifsfdsckName")
        .email("emafdsfsdil")
        .provider(AuthProvider.google)
        .password("passfdsfsdword")
        .provider_id("tedfsfsdfsdfsdfdsst")
        .image_url(null)
        .join_date(new Date())
        .role(Role.USER)
        .build();
  }
}