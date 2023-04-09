package foot.footprint.domain.article.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeleteArticleRepository {

  @Delete("DELETE FROM article WHERE id=#{articleId}")
  int deleteArticle(Long articleId);
}