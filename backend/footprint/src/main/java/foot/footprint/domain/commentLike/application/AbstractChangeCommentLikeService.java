package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.domain.CommentLike;
import foot.footprint.domain.commentLike.dto.ChangeTotalLikesDto;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ObjectSerializer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractChangeCommentLikeService implements ChangeCommentLikeService {

    protected final FindArticleRepository findArticleRepository;

    protected final CommentLikeRepository commentLikeRepository;

    protected final ObjectSerializer objectSerializer;

    protected Article findAndValidateArticle(Long articleId) {
        return findArticleRepository.findById(articleId)
            .orElseThrow(() -> new NotExistsException("해당하는 게시글이 존재하지 않습니다."));
    }

    protected void changeLike(Long commentId, Long memberId, boolean hasILiked) {
        if (hasILiked) {
            deleteLike(commentId, memberId);
            return;
        }
        commentLikeRepository.saveCommentLike(new CommentLike(commentId, memberId));
    }

    private void deleteLike(Long commentId, Long memberId) {
        int deleted = commentLikeRepository.deleteCommentLike(commentId, memberId);
        if (deleted == 0) {
            throw new NotExistsException("이미 좋아요를 취소하였거나 누르지 않았습니다.");
        }
    }

    protected void updateRedis(Long articleId, Long commentId, boolean hasILiked) {
        String redisKey = "articleDetails::" + articleId;
        objectSerializer.updateArticleData(redisKey, ArticleUpdatePart.CHANGE_COMMENT_LIKE,
            new ChangeTotalLikesDto(commentId, hasILiked));
    }
}