package foot.footprint.domain.comment.application.edit;

import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.dao.EditCommentRepository;
import foot.footprint.domain.comment.dto.CommentUpdateDto;
import foot.footprint.global.util.ObjectSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditGeneralCommentService implements EditCommentService {

    private final EditCommentRepository editCommentRepository;

    private final ObjectSerializer objectSerializer;

    @Override
    @Transactional
    public void edit(Long articleId, Long commentId, Long memberId, String newContent) {
        int result = editCommentRepository.editComment(commentId, memberId, newContent);
        if (result == 0) {
            throw new NotMatchMemberException("댓글을 수정할 권한이 없습니다.");
        }
        String redisKey = "articleDetails::" + articleId;
        objectSerializer.updateArticleData(redisKey, ArticleUpdatePart.EDIT_COMMENT,
            new CommentUpdateDto(commentId, newContent));
    }
}