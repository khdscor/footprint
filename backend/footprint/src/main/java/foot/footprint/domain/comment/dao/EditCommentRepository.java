package foot.footprint.domain.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EditCommentRepository {

    @Update("UPDATE comment SET content=#{content} WHERE id=#{commentId} and member_id=#{memberId}")
    int editComment(Long commentId, Long memberId, String content);
}