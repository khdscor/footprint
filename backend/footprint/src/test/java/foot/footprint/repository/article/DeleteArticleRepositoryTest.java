package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.DeleteArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteArticleRepositoryTest extends RepositoryTest {

  @Autowired
  private DeleteArticleRepository deleteArticleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  private FindArticleRepository findArticleRepository;

  @Test
  public void DeleteArticle() {
    //given
    Article createdArticle = setUp();

    //when & then
    assertThat(deleteArticleRepository.deleteArticle(createdArticle.getId())).isEqualTo(1);
  }

  private Article setUp() {
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    return article;
  }
}