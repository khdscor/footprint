package foot.footprint.domain.article.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleMapResponse {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String title;
}
