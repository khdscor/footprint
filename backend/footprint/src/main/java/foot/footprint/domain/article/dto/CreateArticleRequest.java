package foot.footprint.domain.article.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class CreateArticleRequest {

  private String title;
  private String content;
  private double latitude;    // 위도
  private double longitude;   // 경도
  private boolean publicMap;
  private boolean privateMap;
  private List<Long> groupIdsToBeIncluded;

  public CreateArticleRequest(String title, String content, double latitude, double longitude,
      boolean publicMap, boolean privateMap, List<Long> groupIdsToBeIncluded) {
    this.title = title;
    this.content = content;
    this.latitude = latitude;
    this.longitude = longitude;
    this.publicMap = publicMap;
    this.privateMap = privateMap;
    this.groupIdsToBeIncluded = groupIdsToBeIncluded;
  }
}