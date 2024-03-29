package foot.footprint.domain.article.domain;

import java.util.Date;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    }

    public static Article createArticle(
        String title, String content, Double latitude, Double longitude,
        boolean public_map, boolean private_map, Long member_id) {
        return Article.builder()
            .content(content)
            .latitude(latitude)
            .longitude(longitude)
            .public_map(public_map)
            .private_map(private_map)
            .title(title)
            .create_date(new Date())
            .member_id(member_id).build();
    }
}