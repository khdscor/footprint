package foot.footprint.domain.article.dto.articleDetails;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailsDto {

    private Long id;
    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
    private boolean publicMap;
    private boolean privateMap;
    private Long writerId;
    private String writerName;
    private String writerImageUrl;
    private Date createDate;
    private Long totalLikes;

    public void updateTotalLikes(Long num) {
        totalLikes += num;
    }
}