package foot.footprint.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dao.EditCommentRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EdidCommentRepositoryTest extends RepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  private CreateCommentRepository createCommentRepository;

  @Autowired
  private EditCommentRepository editCommentRepository;

  @Autowired
  private FindCommentRepository findCommentRepository;

  @Test
  public void editArticle() {
    //given
    Comment comment = setup();
    String newContent = "수정된 내용";

    //when & then
    assertThat(editCommentRepository.editComment(comment.getId(), newContent))
        .isEqualTo(1);
    assertThat(findCommentRepository.findById(comment.getId()).getContent())
        .isEqualTo(newContent);
  }

  private Comment setup() {
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    Comment comment = buildComment(member.getId(), article.getId());
    createCommentRepository.saveComment(comment);
    return comment;
  }
}