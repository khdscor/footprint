package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface FindArticleRepository {

  List<Article> findArticles(
      Long memberId,
      LocationRange locationRange
  );


}