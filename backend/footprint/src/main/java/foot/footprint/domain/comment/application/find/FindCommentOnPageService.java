package foot.footprint.domain.comment.application.find;

import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.dto.CommentOnPageResponse;
import foot.footprint.domain.comment.dto.CommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindCommentOnPageService implements FindCommentService {

    private final FindCommentRepository findCommentRepository;

    @Override
    @Transactional(readOnly = true)
    public CommentOnPageResponse findComments(Long articleId, Long cursorId) {
        List<CommentResponse> comments = findCommentRepository.findAllByArticleIdOnPage(articleId,
            cursorId);
        if (comments.size() < 11) {
            return new CommentOnPageResponse(comments);
        }
        return new CommentOnPageResponse(comments, comments.get(9).getId());
    }
}