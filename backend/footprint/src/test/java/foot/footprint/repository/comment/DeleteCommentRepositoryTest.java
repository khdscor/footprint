package foot.footprint.repository.comment;


import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dao.DeleteCommentRepository;
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

public class DeleteCommentRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private CreateCommentRepository createCommentRepository;

    @Autowired
    private DeleteCommentRepository deleteCommentRepository;
    @Autowired
    private FindCommentRepository findCommentRepository;

    @Test
    public void deleteArticle() {
        //given
        Long memberId = 1L;
        Long anotherId = 100L;
        Comment comment = setup(memberId);

        //when & then
        assertThat(findCommentRepository.findById(comment.getId()))
            .isNotNull();
        assertThat(deleteCommentRepository.deleteComment(comment.getId(), anotherId))
            .isEqualTo(0);
        assertThat(findCommentRepository.findById(comment.getId()))
            .isNotNull();
        assertThat(deleteCommentRepository.deleteComment(comment.getId(), memberId))
            .isEqualTo(1);
        assertThat(findCommentRepository.findById(comment.getId()))
            .isNull();
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