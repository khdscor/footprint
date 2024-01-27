package foot.footprint.domain.article.dto.articles;

import foot.footprint.domain.article.domain.LocationRange;
import lombok.Getter;

@Getter
public class PrivateArticleMapCommand extends ArticleMapCommand{

    private final Long memberId;

    public PrivateArticleMapCommand(LocationRange locationRange, Long memberId) {
        super(locationRange);
        this.memberId = memberId;
    }

}