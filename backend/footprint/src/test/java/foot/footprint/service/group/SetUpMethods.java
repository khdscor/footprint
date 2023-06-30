package foot.footprint.service.group;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import java.util.Date;

public class SetUpMethods {
    public static Member buildMember() {
        return Member.builder()
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

    public static Article buildArticle(Long memberId) {
        return Article.builder()
            .content("test")
            .latitude(10.0)
            .longitude(10.0)
            .public_map(true)
            .private_map(true)
            .title("test")
            .create_date(new Date())
            .member_id(memberId).build();
    }

    public static ArticleLike buildArticleLike(Long memberId, Long articleId) {
        return ArticleLike.builder()
            .member_id(memberId)
            .article_id(articleId).build();
    }

    public static Comment buildComment(Long ownerId, Long articleId) {
        return Comment.builder()
            .content("아무내용")
            .article_id(articleId)
            .member_id(ownerId)
            .create_date(new Date()).build();
    }

    public static Group buildGroup(Long ownerId) {
        return Group.builder()
            .create_date(new Date())
            .name("test_group")
            .invitation_code("testCode")
            .owner_id(ownerId).build();
    }

    public static MemberGroup buildMemberGroup(Long groupId, Long memberId) {
        return MemberGroup.builder()
            .create_date(new Date())
            .group_id(groupId)
            .member_id(memberId)
            .important(true).build();
    }
}
