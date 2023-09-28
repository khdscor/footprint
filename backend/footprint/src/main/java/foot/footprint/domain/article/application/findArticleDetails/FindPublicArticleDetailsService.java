package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class FindPublicArticleDetailsService extends AbstrastFindArticleDetailsService {

    public FindPublicArticleDetailsService(
        FindArticleRepository findArticleRepository,
        ArticleLikeRepository articleLikeRepository,
        FindCommentRepository findCommentRepository,
        CommentLikeRepository commentLikeRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, articleLikeRepository, findCommentRepository,
            commentLikeRepository, objectSerializer);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails) {
        String redisKey = "articleDetails::" + articleId;
        Optional<ArticlePageResponse> cache = objectSerializer.getData(redisKey,
            ArticlePageResponse.class);
        // redis에 데이터가 있을 경우 - DB 접근 x
        if (cache.isPresent()) {
            validatePublicArticle(cache.get());
            return cache.get();
        }
        // redis에 데이터가 없을 경우 - DB 접근 o
        ArticlePageResponse response = new ArticlePageResponse();
        if (userDetails == null) {
            addNonLoginInfo(articleId, response);
            response.addLoginInfo(false, new ArrayList<>(), -1L);
            return response;
        }
        addLoginInfo(articleId, userDetails.getId(), response);
        validatePublicArticle(response);
        // redis에 저장
        objectSerializer.saveData(redisKey, response, 5);
        return response;
    }

    private void validatePublicArticle(ArticlePageResponse response) {
        if (!response.getArticleDetails().isPublicMap()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
    }
}