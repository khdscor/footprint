package foot.footprint.domain.comment.dao;

import foot.footprint.domain.comment.domain.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreateCommentRepository {

    Long saveComment(Comment comment);

    int saveCommentList(List<Comment> comments);
}
