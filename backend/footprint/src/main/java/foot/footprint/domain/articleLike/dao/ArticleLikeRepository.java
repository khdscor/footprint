package foot.footprint.domain.articleLike.dao;

import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeRepository {

    @Delete("DELETE FROM article_like WHERE article_id=#{articleId} and member_id=#{memberId}")
    int deleteArticleLike(Long articleId, Long memberId);

    int saveArticleLike(ArticleLike articleLike);
}