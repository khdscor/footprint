package foot.footprint.dto.article;


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
