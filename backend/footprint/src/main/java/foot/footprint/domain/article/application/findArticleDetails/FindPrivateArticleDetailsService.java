package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import foot.footprint.global.util.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class FindPrivateArticleDetailsService extends AbstrastFindArticleDetailsService {

    public FindPrivateArticleDetailsService(FindArticleRepository findArticleRepository,
                                            ArticleLikeRepository articleLikeRepository, FindCommentRepository findCommentRepository,
                                            CommentLikeRepository commentLikeRepository, ObjectSerializer objectSerializer) {
        super(findArticleRepository, articleLikeRepository, findCommentRepository,
                commentLikeRepository, objectSerializer);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails) {
        validateMember(userDetails);
        ArticlePageResponse response = new ArticlePageResponse();
        addNonLoginInfo(response, articleId);
        addLoginInfo(articleId, userDetails.getId(), response);
        validatePrivateArticle(response, userDetails.getId());
        return response;
    }

    private void validatePrivateArticle(ArticlePageResponse response, Long myId) {
        if (!response.getArticleDetails().isPrivateMap()) {
            throw new WrongMapTypeException("게시글이 개인지도에 포함되지 않습니다.");
        }
        Validate.validateArticleIsMine(myId, response.getArticleDetails().getWriterId());
    }
}