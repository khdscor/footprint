package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateArticleRepositoryTest extends RepositoryTest {

    @Autowired
    private CreateArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveArticle() {
        //given
        Long memberId = saveOne();
        Article article = buildArticle(memberId);

        //when & then
        assertThat(article.getId()).isNull();
        articleRepository.saveArticle(article);

        //then
        assertThat(article.getId()).isNotNull();
    }

    private Long saveOne() {
        Member member = buildMember();
        memberRepository.saveMember(member);
        return member.getId();
    }
}