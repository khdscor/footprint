package foot.footprint.domain.article.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Timestamp;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class Article {

  private Long id;
  private String content;
  private Timestamp create_date;
  private Double latitude;    // 위도
  private Double longitude;   // 경도
  private boolean private_map;
  private boolean public_map;
  private String title;
  private Long user_id;

  public Article(Long id, String content, Timestamp create_date, Double latitude, Double longitude,
      boolean private_map, boolean public_map, String title, Long user_id) {
    this.id = id;
    this.content = content;
    this.create_date = create_date;
    this.latitude = latitude;
    this.longitude = longitude;
    this.private_map = private_map;
    this.public_map = public_map;
    this.title = title;
    this.user_id = user_id;
  }
}