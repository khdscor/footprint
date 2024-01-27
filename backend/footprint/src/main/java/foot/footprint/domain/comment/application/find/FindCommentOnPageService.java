package foot.footprint.domain.comment.application.find;

import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.dto.CommentsOnPageResponse;
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
    public CommentsOnPageResponse findComments(Long articleId, Long cursorId) {
        List<CommentResponse> comments = findCommentRepository.findAllByArticleIdOnPage(articleId,
            cursorId);
        //10개 이하의 댓글이라면 다음 페이지가 존재하지 않는다.
        if (comments.size() < 11) {
            return new CommentsOnPageResponse(comments);
        }
        //10개 이상의 댓글이라면 다음 페이지가 존재한다.
        return new CommentsOnPageResponse(comments, comments.get(9).getId());
    }
}