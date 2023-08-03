package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.group.domain.ArticleGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreateArticleRepository {

  Long saveArticle(Article article);

  int saveArticleList(List<Article> articles);
}
