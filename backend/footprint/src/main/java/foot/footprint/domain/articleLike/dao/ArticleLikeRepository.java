package foot.footprint.domain.articleLike.dao;

import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeRepository {

    boolean existsMyLike(ArticleLikeDto articleLikeDto);

    @Delete("DELETE FROM article_like WHERE article_id=#{articleId} and member_id=#{memberId}")
    int deleteArticleLike(ArticleLikeDto articleLikeDto);

    int saveArticleLike(ArticleLike articleLike);
}