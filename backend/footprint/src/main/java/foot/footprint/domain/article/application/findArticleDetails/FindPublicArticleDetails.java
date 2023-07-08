package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class FindPublicArticleDetails extends FindArticleDetailsServiceImpl{

    public FindPublicArticleDetails(
        FindArticleRepository findArticleRepository,
        ArticleLikeRepository articleLikeRepository,
        FindCommentRepository findCommentRepository,
        CommentLikeRepository commentLikeRepository) {
        super(findArticleRepository, articleLikeRepository, findCommentRepository,
            commentLikeRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails) {
        findAndValidateArticle(articleId);
        ArticlePageResponse response = new ArticlePageResponse();
        addNonLoginInfo(articleId, response);
        if (userDetails == null) {
            response.addLoginInfo(false, null, null);
            return response;
        }
        addLoginInfo(articleId, userDetails.getId(), response);
        return response;
    }
}