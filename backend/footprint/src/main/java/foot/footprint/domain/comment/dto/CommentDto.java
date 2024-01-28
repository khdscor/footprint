package foot.footprint.domain.comment.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private String commentContent;
    private Long memberId;
    private String nickName;
    private String imageUrl;
    private Date commentCreateDate;
    private Long commentTotalLikes;

    public static CommentDto toDto(CommentResponse response){
        return new CommentDto(response.getId(), response.getContent(), response.getAuthor().getId(),
            response.getAuthor().getNickName(), response.getAuthor().getImageUrl(),
            response.getCreateDate(), response.getTotalLikes());
    }

    public void editContent(String content){
        this.commentContent = content;
    }

    public void updateTotalLikes(Long num) {
        commentTotalLikes += num;
    }
}