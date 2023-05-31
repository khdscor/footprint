package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EditArticleRepositoryTest extends RepositoryTest {

  @Autowired
  private EditArticleRepository editArticleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  private FindArticleRepository findArticleRepository;

  @Test
  public void editArticle() {
    //given
    Long memberId = 1L;
    Long anotherId = 2L;
    Article article = setUp(memberId);
    String newContent = "수정된 내용";

    //when & then
    assertThat(editArticleRepository.editArticle(article.getId(), anotherId, newContent))
        .isEqualTo(0);
    assertThat(editArticleRepository.editArticle(article.getId(), memberId, newContent))
        .isEqualTo(1);
    assertThat(findArticleRepository.findById(article.getId()).get().getContent())
        .isEqualTo(newContent);
  }

  private Article setUp(Long memberId) {
    Member member = buildMember(memberId);
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    return article;
  }

  private Member buildMember(Long memberId) {
    return Member.builder()
        .id(memberId)
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