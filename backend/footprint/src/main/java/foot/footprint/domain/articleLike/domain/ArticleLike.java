package foot.footprint.domain.articleLike.domain;

import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class ArticleLike {

    private Long id;
    private Long article_id;
    private Long member_id;

    public ArticleLike(Long id, Long article_id, Long member_id) {
        this.id = id;
        this.article_id = article_id;
        this.member_id = member_id;
    }

    public static ArticleLike createArticleLike(ArticleLikeDto articleLikeDto) {
        return ArticleLike.builder()
            .member_id(articleLikeDto.getMemberId())
            .article_id(articleLikeDto.getArticleId())
            .build();
    }
}