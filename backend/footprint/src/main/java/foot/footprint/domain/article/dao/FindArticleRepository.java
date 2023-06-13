package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.ArticleDetailsDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FindArticleRepository {

    List<Article> findArticles(
        Long memberId,
        LocationRange locationRange
    );

    List<Article> findArticlesByGroup(
        Long groupId,
        LocationRange locationRange
    );

    @Select("Select * from article where id=#{articleId}")
    Optional<Article> findById(Long articleId);

    ArticleDetailsDto findArticleDetails(Long articleId);
}