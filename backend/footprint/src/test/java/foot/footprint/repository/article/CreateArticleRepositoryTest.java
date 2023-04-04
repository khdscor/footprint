package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateArticleRepositoryTest extends RepositoryTest {

  @Autowired
  private CreateArticleRepository articleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void saveArticle() {
    //given
    Long memberId = saveOne();
    Article article = Article.builder()
        .content("test")
        .latitude(10.1)
        .longitude(10.1)
        .public_map(true)
        .private_map(true)
        .title("test")
        .create_date(new Date())
        .member_id(memberId).build();

    assertThat(articleRepository.saveArticle(article)).isNotNull();
  }

  private Long saveOne() {
    Member member = buildMember();
    return memberRepository.saveMember(member);
  }

  private Member buildMember() {
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
}