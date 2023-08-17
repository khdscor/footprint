package foot.footprint.domain.article.dto;

import foot.footprint.global.domain.AuthorDto;
import java.util.Date;
import lombok.Getter;

@Getter
public class ArticleDetails {

    private Long id;
    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
    private AuthorDto author;
    private Date createDate;
    private Long totalLikes;

    public ArticleDetails(Long id, String title, String content, Double latitude, Double longitude,
        Long userId, String nickName, String imageUrl, Date createDate, Long totalLikes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.author = new AuthorDto(userId, nickName, imageUrl);
        this.createDate = createDate;
        this.totalLikes = totalLikes;
    }

    public static ArticleDetails toArticleDetails(ArticleDetailsDto dto) {
        return new ArticleDetails(dto.getId(), dto.getTitle(), dto.getContent(), dto.getLatitude(),
            dto.getLongitude(), dto.getWriterId(), dto.getWriterName(), dto.getWriterImageUrl(),
            dto.getCreateDate(), dto.getTotalLikes());
    }
}