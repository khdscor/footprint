package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class ChangePublicCommentLike extends ChangeCommentLikeServiceImpl {

    public ChangePublicCommentLike(FindArticleRepository findArticleRepository,
        CommentLikeRepository commentLikeRepository) {
        super(findArticleRepository, commentLikeRepository);
    }

    @Override
    @Transactional
    public void changeMyLike(Long commentId, Long articleId, Boolean hasILiked, Long memberId) {
        Article article = findAndValidateArticle(articleId);
        if (!article.isPublic_map()) {
            throw new NumberFormatException("게시글이 전체지도에 포함되지 않습니다.");
        }
        changeLike(commentId, memberId, hasILiked);
    }
}