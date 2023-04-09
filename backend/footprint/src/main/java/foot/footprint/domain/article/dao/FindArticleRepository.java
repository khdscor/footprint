package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface FindArticleRepository {

  List<Article> findArticles(
      Long memberId,
      LocationRange locationRange
  );

  @Select("Select * from article where id=#{articleId}")
  Optional<Article> findById(Long articleId);
}