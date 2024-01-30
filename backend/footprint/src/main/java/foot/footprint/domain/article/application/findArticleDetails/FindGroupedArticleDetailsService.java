package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("grouped")
public class FindGroupedArticleDetailsService extends AbstrastFindArticleDetailsService {

    private final ArticleGroupRepository articleGroupRepository;

    public FindGroupedArticleDetailsService(
        FindArticleRepository findArticleRepository,
        ArticleLikeRepository articleLikeRepository,
        FindCommentRepository findCommentRepository,
        CommentLikeRepository commentLikeRepository,
        ArticleGroupRepository articleGroupRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, articleLikeRepository, findCommentRepository,
            commentLikeRepository, objectSerializer);
        this.articleGroupRepository = articleGroupRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails) {
        validateMember(userDetails);
        validateInMyGroup(articleId, userDetails.getId());
        ArticlePageResponse response = new ArticlePageResponse();
        addNonLoginInfo(response, articleId);
        addLoginInfo(articleId, userDetails.getId(), response);
        return response;
    }

    private void validateInMyGroup(Long articleId, Long memberId) {
        if (!articleGroupRepository.existsArticleInMyGroup(articleId, memberId)) {
            throw new NotAuthorizedOrExistException("해당글에 접근할 권한이 없습니다.");
        }
    }
}