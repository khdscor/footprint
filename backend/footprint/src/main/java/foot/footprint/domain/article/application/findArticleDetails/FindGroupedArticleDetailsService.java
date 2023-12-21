package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import foot.footprint.global.util.ValidateIsMine;
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
        ValidateIsMine.validateInMyGroup(articleId, userDetails.getId(), articleGroupRepository);
        ArticlePageResponse response = new ArticlePageResponse();
        addNonLoginInfo(response, articleId);
        addLoginInfo(articleId, userDetails.getId(), response);
        return response;
    }
}