package foot.footprint.repository.commentLike;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.domain.CommentLike;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentLIkeRepositoryTest extends RepositoryTest {

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CreateCommentRepository createCommentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Test
    public void saveArticleLike() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        Comment comment = buildComment(member.getId(), article.getId());
        createCommentRepository.saveComment(comment);
        CommentLike commentLike = buildCommentLike(comment.getId(), member.getId());
        //when
        commentLikeRepository.saveCommentLike(commentLike);

        //then
        assertThat(commentLike.getId()).isNotNull();
    }

    @Test
    public void deleteCommentLike() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        Comment comment = buildComment(member.getId(), article.getId());
        createCommentRepository.saveComment(comment);
        CommentLike commentLike = buildCommentLike(comment.getId(), member.getId());
        commentLikeRepository.saveCommentLike(commentLike);

        //when
        int result = commentLikeRepository.deleteCommentLike(comment.getId(), member.getId());

        //that
        assertThat(result).isEqualTo(1);
    }
}