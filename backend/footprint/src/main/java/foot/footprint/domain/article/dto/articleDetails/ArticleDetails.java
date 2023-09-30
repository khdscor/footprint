package foot.footprint.domain.article.dto.articleDetails;

import foot.footprint.global.domain.AuthorDto;
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
    private AuthorDto author;
    private Date createDate;
    private Long totalLikes;

    public ArticleDetails(Long id, String title, String content, Double latitude, Double longitude,
        boolean publicMap, boolean privateMap, Long userId, String nickName, String imageUrl,
        Date createDate, Long totalLikes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.publicMap = publicMap;
        this.privateMap = privateMap;
        this.author = new AuthorDto(userId, nickName, imageUrl);
        this.createDate = createDate;
        this.totalLikes = totalLikes;
    }

    public static ArticleDetails toArticleDetails(ArticleDetailsDto dto) {
        return new ArticleDetails(dto.getId(), dto.getTitle(), dto.getContent(), dto.getLatitude(),
            dto.getLongitude(), dto.isPublicMap(), dto.isPrivateMap(), dto.getWriterId(),
            dto.getWriterName(), dto.getWriterImageUrl(), dto.getCreateDate(), dto.getTotalLikes());
    }

    public void editContent(String content) {
        this.content = content;
    }

    public void updateTotalLikes(Long num) {
        totalLikes += num;
    }
}