package foot.footprint.domain.article.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EditArticleRepository {

  @Update("UPDATE article SET content=#{content} WHERE id=#{articleId}")
  int editArticle(Long articleId, String content);
}