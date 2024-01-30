package foot.footprint.domain.article.dto.articleDetails;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleDetails {

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

    public ArticleDetails(Long id, String title, String content, Double latitude, Double longitude, boolean publicMap,
                          boolean privateMap, Long writerId, String writerName, String writerImageUrl,
                          Date createDate, Long totalLikes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.publicMap = publicMap;
        this.privateMap = privateMap;
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerImageUrl = writerImageUrl;
        this.createDate = createDate;
        this.totalLikes = totalLikes;
    }

    public void updateTotalLikes(Long num) {
        totalLikes += num;
    }

    public void editContent(String content) {
        this.content = content;
    }
}