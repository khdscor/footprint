package foot.footprint.domain.articleLike.dao;

import foot.footprint.domain.articleLike.domain.ArticleLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeRepository {

  int saveArticleLike(ArticleLike articleLike);
}