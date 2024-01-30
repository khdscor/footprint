package foot.footprint.domain.commentLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.commentLike.dto.ChangeCommentLikeCommand;
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
    public void changeMyLike(ChangeCommentLikeCommand command) {
        Article article = findAndValidateArticle((command.getArticleId()));
        if (!article.isPrivate_map()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
        Validate.validateArticleIsMine(article.getMember_id(), command.getMemberId());
        changeLike(command.getCommentId(), command.getMemberId(), command.isHasILiked());
        updateRedis(command.getArticleId(), command.getCommentId(), command.isHasILiked());
    }
}