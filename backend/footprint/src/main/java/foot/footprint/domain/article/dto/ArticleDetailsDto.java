package foot.footprint.domain.article.dto;

import foot.footprint.domain.comment.dto.AuthorDto;
import java.util.Date;
import lombok.Getter;

@Getter
public class ArticleDetailsDto {

  private Long id;
  private String title;
  private String content;
  private Double latitude;
  private Double longitude;
  private AuthorDto author;
  private Date createDate;
  private Long totalLikes;

  public ArticleDetailsDto(Long id, String title, String content, Double latitude, Double longitude,
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
}