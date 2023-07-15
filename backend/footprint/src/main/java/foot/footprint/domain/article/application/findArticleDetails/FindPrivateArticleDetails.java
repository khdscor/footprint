package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ValidateIsMine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class FindPrivateArticleDetails extends FindArticleDetailsServiceImpl{

    public FindPrivateArticleDetails(
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
        Article article = findAndValidateArticle(articleId);
        if (!article.isPrivate_map()) {
            throw new NumberFormatException("게시글이 전체지도에 포함되지 않습니다.");
        }
        ArticlePageResponse response = new ArticlePageResponse();
        validateMember(userDetails);
        ValidateIsMine.validateArticleIsMine(article.getMember_id(), userDetails.getId());
        addNonLoginInfo(article.getId(), response);
        addLoginInfo(article.getId(), userDetails.getId(), response);
        return response;
    }
}