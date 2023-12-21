package foot.footprint.domain.article.dto.articleDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyCommentLikesInArticle {
    private Long commentId;
    private Long memberId;

    public boolean hasMemberId(){
        return this.memberId != null;
    }
}