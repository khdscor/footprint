package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.dto.ChangeCommentLikeCommand;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.util.ObjectSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class ChangePublicCommentLikeService extends AbstractChangeCommentLikeService {

    public ChangePublicCommentLikeService(
        FindArticleRepository findArticleRepository,
        CommentLikeRepository commentLikeRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, commentLikeRepository, objectSerializer);
    }

    @Override
    @Transactional
    public void changeMyLike(ChangeCommentLikeCommand command) {
        Article article = findAndValidateArticle(command.getArticleId());
        if (!article.isPublic_map()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
        changeLike(command.getCommentId(), command.getMemberId(), command.isHasILiked());
        updateRedis(command.getArticleId(), command.getCommentId(), command.isHasILiked());
    }
}