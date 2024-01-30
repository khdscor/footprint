package foot.footprint.domain.article.dto.articles;

import foot.footprint.domain.article.domain.LocationRange;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleMapCommand {

    private LocationRange locationRange;
}