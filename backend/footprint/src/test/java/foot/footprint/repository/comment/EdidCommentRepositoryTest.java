package foot.footprint.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dao.EditCommentRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
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
    Long memberId = 1L;
    Long anotherId = 100L;
    Comment comment = setup(memberId);
    String newContent = "수정된 내용";

    //when & then
    assertThat(editCommentRepository.editComment(comment.getId(), anotherId, newContent))
        .isEqualTo(0);
    assertThat(editCommentRepository.editComment(comment.getId(), memberId, newContent))
        .isEqualTo(1);
    assertThat(findCommentRepository.findById(comment.getId()).getContent())
        .isEqualTo(newContent);
  }

  private Comment setup(Long memberId) {
    Member member = buildMember(memberId);
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    Comment comment = buildComment(member.getId(), article.getId());
    createCommentRepository.saveComment(comment);
    return comment;
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