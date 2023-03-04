package foot.footprint.repository.article;

import foot.footprint.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface findArticleRepository {
    List<Article> findPublicMapArticles(
            @Param("upperLatitude") double upperLatitude,
            @Param("lowerLatitude") double lowerLatitude,
            @Param("upperLongitude") double upperLongitude,
            @Param("lowerLongitude") double lowerLongitude
    );
}
