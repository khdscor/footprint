package foot.footprint.domain.article.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EditArticleRepository {

  @Update("UPDATE article SET content=#{content} WHERE id=#{articleId} and member_id=#{memberId}")
  int editArticle(Long articleId, Long memberId, String content);
}