package foot.footprint.bulkInsert;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.featureFactory.ArticleFeatureFactory;
import foot.footprint.featureFactory.MemberFeatureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreateBulkArticleTest {

    @Autowired
    private CreateArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ArticleGroupRepository articleGroupRepository;
    @Test
    public void create() {
        //given
        EasyRandom memberEasyRandom = MemberFeatureFactory.create();
        Member member = buildMember();
        memberRepository.saveMember(member);
        EasyRandom articleEasyRandom = ArticleFeatureFactory.create(member.getId());

        //when
        var stopWatch = new StopWatch();
        stopWatch.start();
        List<Article> articles = IntStream.range(0, 1000000)
                .parallel()
                .mapToObj(i -> articleEasyRandom.nextObject(Article.class))
                .collect(Collectors.toList());
        stopWatch.stop();
        System.out.println("객체 생성시간: " + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();
        int result = 0;
        for (int i = 0; i < articles.size(); i += 1000) {
            List<Article> articles1 = articles.subList(i, i + 1000);
            result += articleRepository.saveArticleList(articles1);
        }
        queryStopWatch.stop();
        System.out.println("DB 삽입시간: " + (queryStopWatch.getTotalTimeSeconds()));

        //then
        assertThat(result).isEqualTo(articles.size());
        assertThat(articles.get(3330).getId()).isNotNull();
        assertThat(articles.get(3330).getMember_id()).isEqualTo(member.getId());

        //when -그룹 추가
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        var stopWatch2 = new StopWatch();
        stopWatch2.start();
        List<ArticleGroup> articleGroups = articles.stream()
                .parallel()
                .map(article -> ArticleGroup.createArticleGroup(group.getId(), article.getId()))
                .collect(Collectors.toList());
        stopWatch2.stop();
        System.out.println("객체 생성시간: " + stopWatch2.getTotalTimeSeconds());

        var queryStopWatch2 = new StopWatch();
        queryStopWatch2.start();
        int result2 = 0;
        for (int i = 0; i < articleGroups.size(); i += 1000) {
            List<ArticleGroup> articleGroups1 = articleGroups.subList(i, i + 1000);
            result2 += articleGroupRepository.saveArticleGroupList(articleGroups1);
        }
        queryStopWatch2.stop();
        System.out.println("DB 삽입시간: " + (queryStopWatch2.getTotalTimeSeconds()));

        //then
        assertThat(result2).isEqualTo(articles.size());
        assertThat(articles.get(3330).getId()).isNotNull();
        assertThat(articles.get(3330).getMember_id()).isEqualTo(member.getId());
    }

    private Member buildMember() {
        return Member.builder()
                .nick_name("nickName")
                .email("email@naver.com")
                .provider(AuthProvider.local)
                .password("password")
                .provider_id(null)
                .image_url(null)
                .join_date(new Date())
                .role(Role.USER)
                .build();
    }

    private Group buildGroup(Long ownerId) {
        return Group.builder()
                .create_date(new Date())
                .name("test_group")
                .invitation_code("testCode")
                .owner_id(ownerId).build();
    }
}
