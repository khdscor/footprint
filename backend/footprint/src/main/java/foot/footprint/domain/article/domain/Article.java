package foot.footprint.domain.article.domain;

import foot.footprint.domain.article.dto.CreateArticleRequest;
import java.util.Date;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class Article {

    private Long id;
    private String content;
    private Date create_date;
    private Double latitude;    // 위도
    private Double longitude;   // 경도
    private boolean private_map;
    private boolean public_map;
    private String title;
    private Long member_id;
    private final static Long CONTENT_MAN_LENGTH = 10L;
    public Article(Long id, String content, Date create_date, Double latitude, Double longitude,
        boolean private_map, boolean public_map, String title, Long member_id) {
        this.id = id;
        this.content = Objects.requireNonNull(content);
        this.create_date = create_date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.private_map = private_map;
        this.public_map = public_map;
        this.title = title;
        this.member_id = member_id;
        Assert.isTrue(content.length() <= CONTENT_MAN_LENGTH);
    }

    public static Article createArticle(CreateArticleRequest request, Long userId) {
        return Article.builder()
            .content(request.getContent())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .public_map(request.isPublicMap())
            .private_map(request.isPrivateMap())
            .title(request.getTitle())
            .create_date(new Date())
            .member_id(userId).build();
    }
}