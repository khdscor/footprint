package foot.footprint.domain.article.dto.articleDetails;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePrivateDetailsDto {
    private Long articleId;
    private boolean articleLike;
    private List<MyCommentLikesInArticle> commentLikes;
}