package foot.footprint.domain.commentLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTotalLikesDto {

    private Long commentId;
    private boolean hasLiked;
}
