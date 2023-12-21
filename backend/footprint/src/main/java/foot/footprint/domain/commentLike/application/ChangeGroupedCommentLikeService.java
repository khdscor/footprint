package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.util.ObjectSerializer;
import foot.footprint.global.util.ValidateIsMine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("grouped")
public class ChangeGroupedCommentLikeService extends AbstractChangeCommentLikeService {

    private final ArticleGroupRepository articleGroupRepository;

    public ChangeGroupedCommentLikeService(
        FindArticleRepository findArticleRepository,
        CommentLikeRepository commentLikeRepository,
        ArticleGroupRepository articleGroupRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, commentLikeRepository, objectSerializer);
        this.articleGroupRepository = articleGroupRepository;
    }

    @Override
    @Transactional
    public void changeMyLike(Long commentId, Long articleId, Boolean hasILiked, Long memberId) {
        findAndValidateArticle(articleId);
        ValidateIsMine.validateInMyGroup(articleId, memberId, articleGroupRepository);
        changeLike(commentId, memberId, hasILiked);
        updateRedis(articleId, commentId, hasILiked);
    }
}