package foot.footprint.repository.article;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageDto;
import foot.footprint.domain.article.dto.ArticleRangeRequest;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.domain.CommentLike;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.domain.ArticleGroup;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FIndArticleRepositoryTest extends RepositoryTest {

    @Autowired
    private FindArticleRepository findArticleRepository;

    @Autowired
    private CreateArticleRepository createArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ArticleGroupRepository articleGroupRepository;

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private CreateCommentRepository createCommentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Test
    public void findArticlesTest() {
        //given
        saveArticle(10.0, 10.0, true, false);
        saveArticle(35.0, 125.0, false, true);

        //when
        //publicMapArticles
        List<Article> articlesN = findArticleRepository.findPublicArticles(
            new LocationRange(
                new ArticleRangeRequest(10.0, 10.0, 10.0, 10.0))
        );
        //privateMapArticles 다른 유저
        List<Article> articles1 = findArticleRepository.findPrivateArticles(
            1L,
            new LocationRange(
                new ArticleRangeRequest(35.0, 5.0, 125.0, 5.0))
        );
        //privateMapArticles 해당 유저
        List<Article> articles2 = findArticleRepository.findPrivateArticles(
            2L,
            new LocationRange(
                new ArticleRangeRequest(35.0, 5.0, 125.0, 5.0))
        );
        //publicMapArticles 전체지도 but privateArticle만 존재
        List<Article> articles3 = findArticleRepository.findPublicArticles(
            new LocationRange(
                new ArticleRangeRequest(35.0, 5.0, 125.0, 5.0))
        );
        //then
        assertThat(articlesN).hasSize(1);
        assertThat(articles1).hasSize(0);
        assertThat(articles2).hasSize(1);
        assertThat(articles3).hasSize(0);
    }

    @Test
    public void findArticlesByGroupTest() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        ArticleGroup articleGroup = ArticleGroup.createArticleGroup(group.getId(), article.getId());
        List<ArticleGroup> articleGroups = new ArrayList<>();
        articleGroups.add(articleGroup);
        articleGroupRepository.saveArticleGroupList(articleGroups);

        //when
        List<Article> articles = findArticleRepository.findArticlesByGroup(group.getId(),
            new LocationRange(new ArticleRangeRequest(5.0, 10.0, 5.0, 10.0)));
        List<Article> articles2 = findArticleRepository.findArticlesByGroup(100L,
            new LocationRange(new ArticleRangeRequest(5.0, 10.0, 5.0, 10.0)));

        //then
        assertThat(articles).hasSize(1);
        assertThat(articles2).hasSize(0);
    }

    @Test
    public void findArticle() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Article article = buildArticle(member.getId());
        createArticleRepository.saveArticle(article);

        //when
        Optional<Article> savedArticle = findArticleRepository.findById(article.getId());

        //then
        assertThat(article.getId()).isEqualTo(savedArticle.get().getId());
        assertThat(article.getCreate_date()).isEqualTo(savedArticle.get().getCreate_date());
        assertThat(article.getTitle()).isEqualTo(savedArticle.get().getTitle());
    }

    @Test
    public void findArticleDetails() {
        //given
        Member member1 = buildMember();
        memberRepository.saveMember(member1);
        Member member2 = buildMember();
        memberRepository.saveMember(member2);

        Article article = buildArticle(member1.getId());
        createArticleRepository.saveArticle(article);
        Article dummyArticle1 = buildArticle(member1.getId());
        createArticleRepository.saveArticle(dummyArticle1);
        Article dummyArticle2 = buildArticle(member2.getId());
        createArticleRepository.saveArticle(dummyArticle2);

        ArticleLike articleLike1 = buildArticleLike(member1.getId(), article.getId());
        articleLikeRepository.saveArticleLike(articleLike1);
        ArticleLike articleLike2 = buildArticleLike(member2.getId(), article.getId());
        articleLikeRepository.saveArticleLike(articleLike2);

        Comment comment1 = buildComment(member1.getId(), article.getId());
        Comment comment2 = buildComment(member1.getId(), dummyArticle1.getId());
        Comment comment3 = buildComment(member2.getId(), article.getId());
        createCommentRepository.saveComment(comment1);
        createCommentRepository.saveComment(comment2);
        createCommentRepository.saveComment(comment3);

        CommentLike commentLike1 = buildCommentLike(comment1.getId(), member1.getId());
        CommentLike commentLike2 = buildCommentLike(comment1.getId(), member2.getId());
        CommentLike commentLike3 = buildCommentLike(comment3.getId(), member1.getId());
        commentLikeRepository.saveCommentLike(commentLike1);
        commentLikeRepository.saveCommentLike(commentLike2);
        commentLikeRepository.saveCommentLike(commentLike3);

        //when
        Optional<ArticlePageDto> dto = findArticleRepository.findArticleDetails(article.getId(),
            member1.getId());

        //then
        assertThat(dto).isPresent();
        assertThat(dto.get().getArticleId()).isEqualTo(article.getId());
        assertThat(dto.get().getArticleDetails().getContent()).isEqualTo(article.getContent());
        assertThat(dto.get().getArticleDetails().getTotalLikes()).isEqualTo(2);
        assertThat(dto.get().isArticleLike()).isTrue();
        assertThat(dto.get().getComments().size()).isEqualTo(2);
        assertThat(dto.get().getComments().get(0).getNickName()).isEqualTo(member2.getNick_name());
        assertThat(dto.get().getComments().get(0).getMemberId()).isEqualTo(member2.getId());
        assertThat(dto.get().getComments().get(1).getMemberId()).isEqualTo(member1.getId());
        assertThat(dto.get().getComments().get(0).getCommentTotalLikes()).isEqualTo(1L);
        assertThat(dto.get().getComments().get(1).getCommentTotalLikes()).isEqualTo(2L);

        //comments 가 10개로 limit test
        //given
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            comments.add(buildComment(member1.getId(), article.getId()));
        }
        int resultComment = createCommentRepository.saveCommentList(comments);

        assertThat(resultComment).isEqualTo(13);

        //when
        Optional<ArticlePageDto> dto2 = findArticleRepository.findArticleDetails(article.getId(),
            member1.getId());

        //then
        assertThat(dto2).isPresent();
        assertThat(dto2.get().getComments()).hasSize(10);

        //when & then : 로그인 하지 않았을 시
        Optional<ArticlePageDto> dto3 = findArticleRepository.findArticleDetails(article.getId(),
                null);
        assertThat(dto3).isPresent();
        assertThat(dto3.get().isArticleLike()).isFalse();
    }

    private void saveArticle(double lat, double lng, boolean publicMap, boolean privateMap) {
        Member member = buildMember();
        memberRepository.saveMember(member);
        System.out.println("memberId: " + member.getId());
        Article article = Article.builder()
            .content("test")
            .latitude(lat)
            .longitude(lng)
            .public_map(publicMap)
            .private_map(privateMap)
            .title("test")
            .create_date(new Date())
            .member_id(member.getId()).build();
        createArticleRepository.saveArticle(article);
    }
}