package foot.footprint.domain.comment.application.delete;

import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.dao.DeleteCommentRepository;
import foot.footprint.global.util.ObjectSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteGeneralCommentService implements DeleteCommentService {

    private final DeleteCommentRepository deleteCommentRepository;

    private final ObjectSerializer objectSerializer;

    @Override
    @Transactional
    public void delete(Long articleId, Long commentId, Long memberId) {
        int result = deleteCommentRepository.deleteComment(commentId, memberId);
        if (result == 0) {
            throw new NotMatchMemberException("댓글을 삭제할 권한이 없습니다.");
        }
        String redisKey = "articleDetails::" + articleId + ":" + memberId;
        objectSerializer.updateArticleData(redisKey, ArticleUpdatePart.REMOVE_COMMENT, commentId);
    }
}