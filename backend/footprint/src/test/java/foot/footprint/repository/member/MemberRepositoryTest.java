package foot.footprint.repository.member;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.dto.MyPageResponse;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private CreateCommentRepository createCommentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private FindArticleRepository findArticleRepository;

    private String Email = "email";

    @Test
    public void saveMember() {
        //given
        Member member = buildMember();

        //when
        memberRepository.saveMember(member);

        //then
        assertThat(member.getId()).isNotNull();
    }

    @Test
    public void existsByEmail() {
        //given
        saveOne();

        //when & then
        assertThat(memberRepository.existsByEmail("test")).isFalse();
        assertThat(memberRepository.existsByEmail(Email)).isTrue();
    }

    @Test
    public void findByEmail() {
        //given
        saveOne();

        //when & then
        assertThat(memberRepository.findByEmail(Email).get().getNick_name()).isEqualTo("nickName");
    }

    @Test
    public void findMyPageDetails() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        Comment comment = buildComment(member.getId(), article.getId());
        ArticleLike articleLike = buildArticleLike(member.getId(), article.getId());
        articleLikeRepository.saveArticleLike(articleLike);
        createCommentRepository.saveComment(comment);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);

        //when
        MyPageResponse response = memberRepository.findMyPageDetails(member.getId());

        //then
        assertThat(response.getMyInfo().getEmail()).isEqualTo(member.getEmail());
        assertThat(response.getMyArticles()).hasSize(1);
        assertThat(response.getMyArticles().get(0).getTotalLikes()).isEqualTo(1);
        assertThat(response.getMyArticles().get(0).getTotalComments()).isEqualTo(1);
        assertThat(response.getMyArticles().get(0).getTitle()).isEqualTo(article.getTitle());
        assertThat(response.getMyGroups()).hasSize(1);
        assertThat(response.getMyGroups().get(0).getGroupId()).isEqualTo(group.getId());
    }

    @Test
    public void deleteMember(){
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);

        //when
        int deleted = memberRepository.deleteMember(member.getId());

        //then
        assertThat(deleted).isEqualTo(1);
        assertThat(memberRepository.findById(member.getId())).isEmpty();
        assertThat(findArticleRepository.findById(article.getId())).isEmpty();
    }

    private void saveOne() {
        Member member = buildMember();
        memberRepository.saveMember(member);
    }
}