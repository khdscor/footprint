package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreateArticleRepository {

    Long saveArticle(Article article);
}
