package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.global.domain.AuthorDto;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.util.ObjectSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("grouped")
public class CreateCommentOnGroupedArticle extends AbstractCreateCommentService {

    private final ArticleGroupRepository articleGroupRepository;

    public CreateCommentOnGroupedArticle(
        FindArticleRepository findArticleRepository,
        MemberRepository memberRepository,
        CreateCommentRepository createCommentRepository,
        ArticleGroupRepository articleGroupRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, memberRepository, createCommentRepository, objectSerializer);
        this.articleGroupRepository = articleGroupRepository;
    }

    @Override
    @Transactional
    public CommentResponse createComment(Long articleId, String content, Long memberId) {
        findAndValidateArticle(articleId);
        Member member = findAndValidateMember(memberId);
        AuthorDto authorDto = AuthorDto.buildAuthorDto(member);
        validateInMyGroup(articleId, authorDto.getId());
        CommentResponse response = saveComment(articleId, content, authorDto);
        updateRedis(articleId, response);
        return response;
    }

    private void validateInMyGroup(Long articleId, Long memberId) {
        if (!articleGroupRepository.existsArticleInMyGroup(articleId, memberId)) {
            throw new NotAuthorizedOrExistException("해당글에 접근할 권한이 없습니다.");
        }
    }
}