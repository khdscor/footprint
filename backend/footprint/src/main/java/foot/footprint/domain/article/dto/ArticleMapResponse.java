package foot.footprint.domain.article.dto;

import foot.footprint.domain.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class ArticleMapResponse {

    private Long id;
    private Double latitude;
    private Double longitude;
    private String title;

    public static List<ArticleMapResponse> toResponses(List<Article> articles) {
        if (Objects.isNull(articles)) {
            return Collections.emptyList();
        }
        return articles.stream()
            .map(article -> new ArticleMapResponse(
                article.getId(),
                article.getLatitude(),
                article.getLongitude(),
                article.getTitle()))
            .collect(Collectors.toUnmodifiableList());
    }
}