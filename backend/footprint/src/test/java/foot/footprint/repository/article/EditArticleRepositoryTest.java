package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
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
    Article article = setup();
    String newContent = "수정된 내용";

    //when & then
    assertThat(editArticleRepository.editArticle(article.getId(), newContent))
        .isEqualTo(1);
    assertThat(findArticleRepository.findById(article.getId()).get().getContent())
        .isEqualTo(newContent);
  }

  private Article setup(){
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    return article;
  }
}