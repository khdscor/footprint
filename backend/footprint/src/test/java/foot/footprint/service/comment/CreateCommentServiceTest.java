package foot.footprint.service.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.application.CreateCommentService;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateCommentServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FindArticleRepository findArticleRepository;

    @Mock
    private ArticleGroupRepository articleGroupRepository;

    @Mock
    private CreateCommentRepository createCommentRepository;

    @InjectMocks
    private CreateCommentService createCommentService;

    @Test
    @DisplayName("댓글 작성 테스트 -- 전체 지도")
    public void create_IfPublicMap() {
        //given
        Long memberId = 10L;
        String content = "이것은 테스트 입니다.";
        given(memberRepository.findById(any()))
            .willReturn(Optional.ofNullable(createMember(memberId)));
        given(findArticleRepository.findById(any())).willReturn(
            Optional.ofNullable(createArticle(memberId, false, true)));

        //when
        CommentResponse response = createCommentService.create(1L, content, memberId);

        //then
        assertThat(response.getAuthor().getId()).isEqualTo(memberId);
        assertThat(response.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("댓글 작성 테스트 -- 전체 지도")
    public void create_IfPrivateMap() {
        //given
        Long memberId = 10L;
        Long anotherId = 20L;
        String content = "이것은 테스트 입니다.";
        given(memberRepository.findById(any()))
            .willReturn(Optional.ofNullable(createMember(memberId)));
        given(findArticleRepository.findById(any())).willReturn(
            Optional.ofNullable(createArticle(memberId, true, false)));

        //when
        CommentResponse response = createCommentService.create(1L, content, memberId);

        //then
        assertThat(response.getAuthor().getId()).isEqualTo(memberId);
        assertThat(response.getContent()).isEqualTo(content);

        //given 개인 권한이 없을 시 예외 처리
        given(memberRepository.findById(any()))
            .willReturn(Optional.ofNullable(createMember(anotherId)));

        //when & then
        assertThatThrownBy(
            () -> createCommentService.create(1L, content, anotherId))
            .isInstanceOf(NotMatchMemberException.class);
    }

    @Test
    @DisplayName("댓글 작성 테스트 -- 그룹지도 예외처리")
    public void create_IfGroupedMap() {
        //given
        Long memberId = 10L;
        String content = "이것은 테스트 입니다.";
        given(memberRepository.findById(any()))
            .willReturn(Optional.ofNullable(createMember(memberId)));
        given(findArticleRepository.findById(any())).willReturn(
            Optional.ofNullable(createArticle(memberId, false, false)));
        given(articleGroupRepository.existsArticleInMyGroup(any(), any())).willReturn(false);

        //when & then
        assertThatThrownBy(
            () -> createCommentService.create(1L, content, memberId))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    private Article createArticle(Long memberId, boolean privateMap, boolean publicMap) {
        return Article.builder().id(1L).content("ddddd").latitude(10.1).longitude(10.1)
            .private_map(privateMap).public_map(publicMap).title("히히히히").member_id(memberId)
            .build();
    }

    private Member createMember(Long memberId) {
        return Member.builder().id(memberId).email("test").image_url("테스트입니다").provider_id("test")
            .provider(AuthProvider.google).nick_name("tset").role(Role.USER).join_date(new Date())
            .password("password").build();
    }
}