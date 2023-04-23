package foot.footprint.domain.articleLike.dao;

import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeRepository {

  boolean existsMyLike(ArticleLikeDto articleLikeDto);

  int saveArticleLike(ArticleLike articleLike);
}