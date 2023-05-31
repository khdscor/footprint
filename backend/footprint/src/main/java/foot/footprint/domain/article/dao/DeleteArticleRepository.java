package foot.footprint.domain.article.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeleteArticleRepository {

  @Delete("DELETE FROM article WHERE id=#{articleId} and member_id=#{memberId}")
  int deleteById(Long articleId, Long memberId);
}