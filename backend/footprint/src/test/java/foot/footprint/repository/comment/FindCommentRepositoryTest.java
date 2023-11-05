package foot.footprint.repository.comment;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.domain.CommentLike;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.featureFactory.CommentFeatureFactory;
import foot.footprint.repository.RepositoryTest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FindCommentRepositoryTest extends RepositoryTest {

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CreateCommentRepository createCommentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private FindCommentRepository findCommentRepository;

    @Test
    public void findAllByArticleId() {
        //given
        Member member1 = buildMember();
        memberRepository.saveMember(member1);
        Member member2 = buildMember();
        memberRepository.saveMember(member2);
        Article article = buildArticle(member1.getId());
        createArticleRepository.saveArticle(article);
        //2개의 댓글
        Comment comment1 = buildComment(member1.getId(), article.getId());
        createCommentRepository.saveComment(comment1);
        Comment comment2 = buildComment(member2.getId(), article.getId());
        createCommentRepository.saveComment(comment2);
        //3개의 좋아요
        CommentLike commentLike1 = buildCommentLike(comment1.getId(), member1.getId());
        commentLikeRepository.saveCommentLike(commentLike1);
        CommentLike commentLike2 = buildCommentLike(comment1.getId(), member2.getId());
        commentLikeRepository.saveCommentLike(commentLike2);
        CommentLike commentLike3 = buildCommentLike(comment2.getId(), member1.getId());
        commentLikeRepository.saveCommentLike(commentLike3);

        //when
        List<CommentResponse> responses = findCommentRepository.findAllByArticleId(article.getId());

        //then
        assertThat(responses).hasSize(2);
    }

    @Test
    public void findAllByArticleIdOnPage() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        //2개의 댓글

        EasyRandom easyRandom = CommentFeatureFactory.create(-1L, article.getId(), member.getId());
        List<Comment> comments = IntStream.range(0, 200)
            .parallel()
            .mapToObj(i -> easyRandom.nextObject(Comment.class))
            .collect(Collectors.toList());
        createCommentRepository.saveCommentList(comments);
        Long cursorId = 121L;
        //when

        List<CommentResponse> responses = findCommentRepository.findAllByArticleIdOnPage(
            article.getId(), cursorId);

        //then
        assertThat(responses).hasSize(11);
        assertThat(responses.get(0).getId()).isEqualTo(120L);
    }
}