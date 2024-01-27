package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.util.ObjectSerializer;
import foot.footprint.global.util.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class ChangePrivateCommentLikeService extends AbstractChangeCommentLikeService {

    public ChangePrivateCommentLikeService(
        FindArticleRepository findArticleRepository,
        CommentLikeRepository commentLikeRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, commentLikeRepository, objectSerializer);
    }

    @Override
    @Transactional
    public void changeMyLike(Long commentId, Long articleId, Boolean hasILiked, Long memberId) {
        Article article = findAndValidateArticle(articleId);
        if (!article.isPrivate_map()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
        Validate.validateArticleIsMine(article.getMember_id(), memberId);
        changeLike(commentId, memberId, hasILiked);
        updateRedis(articleId, commentId, hasILiked);
    }
}