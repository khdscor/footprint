package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class FindPublicArticleDetailsService extends AbstrastFindArticleDetailsService {

    public FindPublicArticleDetailsService(FindArticleRepository findArticleRepository,
        ArticleLikeRepository articleLikeRepository, FindCommentRepository findCommentRepository,
        CommentLikeRepository commentLikeRepository, ObjectSerializer objectSerializer) {
        super(findArticleRepository, articleLikeRepository, findCommentRepository,
            commentLikeRepository, objectSerializer);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails) {
        ArticlePageResponse response = new ArticlePageResponse();
        addNonLoginInfo(response, articleId);
        validatePublicArticle(response);
        // 로그인하지 않았을 경우
        if (userDetails == null) {
            response.addLoginInfo(false, new ArrayList<>(), -1L);
            return response;
        }
        // 로그인하였을 경우
        addLoginInfo(articleId, userDetails.getId(), response);
        return response;
    }

    private void validatePublicArticle(ArticlePageResponse response) {
        if (!response.getArticleDetails().isPublicMap()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
    }
}