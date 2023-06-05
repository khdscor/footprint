package foot.footprint.repository;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import java.util.Date;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
@ActiveProfiles("test")
@Sql(value = "/init-table.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class RepositoryTest {

  protected Member buildMember() {
    return Member.builder()
        .nick_name("nickName")
        .email("email")
        .provider(AuthProvider.google)
        .password("password")
        .provider_id("test")
        .image_url(null)
        .join_date(new Date())
        .role(Role.USER)
        .build();
  }

  protected Article buildArticle(Long memberId) {
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

  protected ArticleLike buildArticleLike(Long memberId, Long articleId) {
    return ArticleLike.builder()
        .member_id(memberId)
        .article_id(articleId).build();
  }

  protected Comment buildComment(Long ownerId, Long articleId) {
    return Comment.builder()
        .content("아무내용")
        .article_id(articleId)
        .member_id(ownerId)
        .create_date(new Date()).build();
  }

  protected Group buildGroup(Long ownerId) {
    return Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code("testCode")
        .owner_id(ownerId).build();
  }
}