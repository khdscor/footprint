package foot.footprint.domain.article.dao;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.domain.LocationRange;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FindArticleRepository {

    List<Article> findPublicArticles(LocationRange locationRange);

    List<Article> findPrivateArticles(Long memberId, LocationRange locationRange);

    List<Article> findArticlesByGroup(Long groupId, LocationRange locationRange);

    @Select("select * from article order by id")
    List<Article> findAll();

    @Select("Select * from article where id=#{articleId}")
    Optional<Article> findById(Long articleId);

    Optional<ArticlePageDto> findArticleDetails(Long articleId, Long memberId);
}