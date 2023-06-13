package foot.footprint.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateCommentRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private CreateCommentRepository createCommentRepository;

    @Test
    public void createComment() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        Comment comment = buildComment(member.getId(), article.getId());

        //when
        createCommentRepository.saveComment(comment);

        //then
        assertThat(comment.getId()).isNotNull();
    }
}