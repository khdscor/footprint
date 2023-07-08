package foot.footprint.domain.comment.application.delete;

import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.dao.DeleteCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCommentServiceImpl implements DeleteCommentService {

    private final DeleteCommentRepository deleteCommentRepository;

    @Override
    @Transactional
    public void delete(Long commentId, Long memberId) {
        int result = deleteCommentRepository.deleteComment(commentId, memberId);
        if (result == 0) {
            throw new NotMatchMemberException("댓글을 삭제할 권한이 없습니다.");
        }
    }
}