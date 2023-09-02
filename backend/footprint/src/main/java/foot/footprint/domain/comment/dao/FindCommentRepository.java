package foot.footprint.domain.comment.dao;

import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.comment.dto.CommentResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FindCommentRepository {

    @Select("select * from comment where id =#{commentId}")
    Comment findById(Long commentId);

    List<CommentResponse> findAllByArticleId(Long articleId);

    List<CommentResponse> findAllByArticleIdOnPage(Long articleId, Long cursorId);
}