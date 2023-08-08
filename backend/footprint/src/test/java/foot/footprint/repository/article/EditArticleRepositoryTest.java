package foot.footprint.repository.article;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.featureFactory.MemberFeatureFactory;
import foot.footprint.repository.RepositoryTest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EditArticleRepositoryTest extends RepositoryTest {

    @Autowired
    private EditArticleRepository editArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private FindArticleRepository findArticleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @Autowired
    private ArticleGroupRepository articleGroupRepository;

    @Test
    public void editArticle() {
        //given
        Long memberId = 1L;
        Long anotherId = 2L;
        Article article = setUp(memberId);
        String newContent = "수정된 내용";

        //when & then
        assertThat(editArticleRepository.editArticle(article.getId(), anotherId, newContent))
            .isEqualTo(0);
        assertThat(editArticleRepository.editArticle(article.getId(), memberId, newContent))
            .isEqualTo(1);
        assertThat(findArticleRepository.findById(article.getId()).get().getContent())
            .isEqualTo(newContent);
    }

    @Test
    public void updatePrivateMapTrue() {
        //given
        EasyRandom memberEasyRandom = MemberFeatureFactory.create();
        Member member1 = memberEasyRandom.nextObject(Member.class);
        memberRepository.saveMember(member1);

        List<Article> articles = new ArrayList<>();
        articles.add(buildArticle(member1.getId()));
        articles.add(buildArticle(member1.getId()));
        articles.add(buildArticle(member1.getId()));
        createArticleRepository.saveArticleList(articles);
        Group group = buildGroup(member1.getId());
        groupRepository.saveGroup(group);
        memberGroupRepository.saveMemberGroup(buildMemberGroup(group.getId(), member1.getId()));
        List<ArticleGroup> articleGroups = new ArrayList<>();
        articleGroups.add(ArticleGroup.createArticleGroup(group.getId(), articles.get(0).getId()));
        articleGroups.add(ArticleGroup.createArticleGroup(group.getId(), articles.get(1).getId()));
        articleGroupRepository.saveArticleGroupList(articleGroups);

        //when
        int result = editArticleRepository.updatePrivateMapTrue(member1.getId(), group.getId());
        List<Article> updatedArticles = findArticleRepository.findAll();

        //then
        assertThat(result).isEqualTo(2);
        assertThat(updatedArticles.get(0).isPrivate_map()).isTrue();
        assertThat(updatedArticles.get(1).isPrivate_map()).isTrue();
        assertThat(updatedArticles.get(2).isPrivate_map()).isFalse();
    }

    private Article setUp(Long memberId) {
        Member member = buildMember(memberId);
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        return article;
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