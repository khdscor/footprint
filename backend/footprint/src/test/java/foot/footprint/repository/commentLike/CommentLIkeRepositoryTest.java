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
        CommentLike commentLike = createCommentLike(comment.getId(), member.getId());
        //when
        commentLikeRepository.saveCommentLike(commentLike);

        //then
        assertThat(commentLike.getId()).isNotNull();
    }

    @Test
    public void findHasILiked() {
        //given
        // 2명의 유저
        Member member = buildMember();
        memberRepository.saveMember(member);
        Member another = buildMember();
        memberRepository.saveMember(another);
        //게시글 생성
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        //4개의 댓글, 3개는 member, 1개는 another
        Comment comment1 = buildComment(member.getId(), article.getId());
        createCommentRepository.saveComment(comment1);
        Comment comment2 = buildComment(member.getId(), article.getId());
        createCommentRepository.saveComment(comment2);
        Comment comment3 = buildComment(member.getId(), article.getId());
        createCommentRepository.saveComment(comment3);
        Comment comment4 = buildComment(another.getId(), article.getId());
        createCommentRepository.saveComment(comment4);
        //3개의 좋아요, 2개는 member 1개는 another
        CommentLike commentLike1 = createCommentLike(comment1.getId(), member.getId());
        commentLikeRepository.saveCommentLike(commentLike1);
        CommentLike commentLike2 = createCommentLike(comment2.getId(), another.getId());
        commentLikeRepository.saveCommentLike(commentLike2);
        CommentLike commentLike4 = createCommentLike(comment4.getId(), member.getId());
        commentLikeRepository.saveCommentLike(commentLike4);

        //when
        List<Long> commentLikes = commentLikeRepository.findCommentIdsILiked(article.getId(),
            member.getId());

        //then
        assertThat(commentLikes).hasSize(2);
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
        CommentLike commentLike = createCommentLike(comment.getId(), member.getId());
        commentLikeRepository.saveCommentLike(commentLike);

        //when
        int result = commentLikeRepository.deleteCommentLike(comment.getId(), member.getId());

        //that
        assertThat(result).isEqualTo(1);
    }

    private CommentLike createCommentLike(Long commentId, Long memberId) {
        return CommentLike.builder()
            .comment_id(commentId)
            .member_id(memberId).build();
    }
}