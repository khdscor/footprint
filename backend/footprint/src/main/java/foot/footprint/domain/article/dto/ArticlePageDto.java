package foot.footprint.domain.article.dto;

import foot.footprint.domain.comment.dto.CommentsDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageDto {
    private Long articleId;
    private ArticleDetailsDto articleDetails;
    private boolean articleLike;
    private List<CommentsDto> comments;
}
