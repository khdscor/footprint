package foot.footprint.domain.member.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyArticleResponse {

    private Long articleId;
    private String title;
    private Date createDate;
    private boolean publicMap;
    private boolean privateMap;
    private Long totalLikes;
    private Long totalComments;
}