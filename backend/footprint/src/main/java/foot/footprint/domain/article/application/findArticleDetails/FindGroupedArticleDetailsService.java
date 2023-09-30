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
import java.util.Optional;
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
        String redisKey = "articleDetails::" + articleId;
        Optional<ArticlePageResponse> cache = objectSerializer.getData(redisKey,
            ArticlePageResponse.class);
        // redis에 데이터가 있을 경우 - DB 접근 x
        if (cache.isPresent()) {
            return cache.get();
        }
        // redis에 데이터가 없을 경우 - DB 접근 o
        ArticlePageResponse response = new ArticlePageResponse();
        addLoginInfo(articleId, userDetails.getId(), response);
        // redis에 저장
        objectSerializer.saveData(redisKey, response, 30);
        return response;
    }
}