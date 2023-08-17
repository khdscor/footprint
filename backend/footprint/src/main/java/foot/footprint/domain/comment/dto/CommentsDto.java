package foot.footprint.domain.comment.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {

    private Long commentId;
    private String commentContent;
    private Long memberId;
    private String nickName;
    private String imageUrl;
    private Date commentCreateDate;
    private Long commentTotalLikes;
}