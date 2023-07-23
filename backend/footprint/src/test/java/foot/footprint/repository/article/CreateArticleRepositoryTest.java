package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.featureFactory.ArticleFeatureFactory;
import foot.footprint.repository.RepositoryTest;
import foot.footprint.repository.featureFactory.MemberFeatureFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jeasy.random.EasyRandom;
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

    @Test
    public void create(){
        //given
        EasyRandom memberEasyRandom = MemberFeatureFactory.create();
        Member member = memberEasyRandom.nextObject(Member.class);
        memberRepository.saveMember(member);
        EasyRandom articleEasyRandom = ArticleFeatureFactory.create(member.getId());

        //when
        List<Article> articles = IntStream.range(0, 1000)
            .parallel()
            .mapToObj(i -> articleEasyRandom.nextObject(Article.class))
            .collect(Collectors.toList());
        int result = articleRepository.saveArticleList(articles);

        //then
        assertThat(result).isEqualTo(articles.size());
        assertThat(articles.get(0).getId()).isNotNull();
        assertThat(articles.get(0).getMember_id()).isEqualTo(member.getId());
    }

    private Long saveOne() {
        Member member = buildMember();
        memberRepository.saveMember(member);
        return member.getId();
    }
}