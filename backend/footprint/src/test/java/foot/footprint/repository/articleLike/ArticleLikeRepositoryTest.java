package foot.footprint.repository.articleLike;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleLikeRepositoryTest extends RepositoryTest {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveArticleLike() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        ArticleLike articleLike = createArticleLike(article.getId(), member.getId());

        //when
        articleLikeRepository.saveArticleLike(articleLike);

        //then
        assertThat(articleLike.getId()).isNotNull();
    }

    @Test
    public void deleteArticleLike() {
        //given
        ArticleLike articleLike = setUpArticleLike();
        ArticleLikeCommand command = new ArticleLikeCommand(articleLike.getArticle_id(),
            articleLike.getMember_id());

        //when
        int result = articleLikeRepository.deleteArticleLike(command.getArticleId(),
            command.getMemberId());

        //then
        assertThat(result).isEqualTo(1);
    }

    private ArticleLike createArticleLike(Long articleId, Long memberId) {
        return ArticleLike.builder()
            .article_id(articleId)
            .member_id(memberId).build();
    }

    private ArticleLike setUpArticleLike() {
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        ArticleLike articleLike = createArticleLike(article.getId(), member.getId());
        articleLikeRepository.saveArticleLike(articleLike);

        return articleLike;
    }
}