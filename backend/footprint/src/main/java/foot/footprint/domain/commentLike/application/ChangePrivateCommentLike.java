package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.util.ValidateIsMine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class ChangePrivateCommentLike extends ChangeCommentLikeServiceImpl {

    public ChangePrivateCommentLike(FindArticleRepository findArticleRepository,
        CommentLikeRepository commentLikeRepository) {
        super(findArticleRepository, commentLikeRepository);
    }

    @Override
    @Transactional
    public void changeMyLike(Long commentId, Long articleId, Boolean hasILiked, Long memberId) {
        Article article = findAndValidateArticle(articleId);
        ValidateIsMine.validateArticleIsMine(article.getMember_id(), memberId);
        changeLike(commentId, memberId, hasILiked);
    }
}