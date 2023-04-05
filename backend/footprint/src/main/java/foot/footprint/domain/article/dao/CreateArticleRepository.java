package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreateArticleRepository {

  Long saveArticle(Article article);
}
