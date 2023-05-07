package foot.footprint.repository.group;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleGroupRepositoryTest extends RepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private ArticleGroupRepository articleGroupRepository;

  @Autowired
  private MemberGroupRepository memberGroupRepository;

  @Test
  public void saveArticleGroupList() {
    //given
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article1 = buildArticle(member.getId());
    Article article2 = buildArticle(member.getId());
    createArticleRepository.saveArticle(article1);
    createArticleRepository.saveArticle(article2);
    Group group = buildGroup(member.getId());
    groupRepository.saveGroup(group);
    List<ArticleGroup> articleGroups = new ArrayList<>();
    articleGroups.add(buildArticleGroup(group.getId(), article1.getId()));
    articleGroups.add(buildArticleGroup(group.getId(), article2.getId()));

    //when
    int result = articleGroupRepository.saveArticleGroupList(articleGroups);

    //then
    assertThat(result).isEqualTo(2);

  }

  @Test
  public void existsArticleInMyGroup() {
    //given
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    Group group = buildGroup(member.getId());
    groupRepository.saveGroup(group);
    List<ArticleGroup> articleGroups = new ArrayList<>();
    articleGroups.add(buildArticleGroup(group.getId(), article.getId()));
    articleGroupRepository.saveArticleGroupList(articleGroups);
    MemberGroup memberGroup = MemberGroup.createMemberGroup(group);
    memberGroupRepository.saveMemberGroup(memberGroup);

    //when & then
    assertThat(
        articleGroupRepository.existsArticleInMyGroup(article.getId(), member.getId())).isTrue();
    assertThat(
        articleGroupRepository.existsArticleInMyGroup(100L, member.getId())).isFalse();
    assertThat(
        articleGroupRepository.existsArticleInMyGroup(article.getId(), 100L)).isFalse();

  }

  private ArticleGroup buildArticleGroup(Long groupId, Long articleId) {
    return ArticleGroup.createArticleGroup(groupId, articleId);
  }
}