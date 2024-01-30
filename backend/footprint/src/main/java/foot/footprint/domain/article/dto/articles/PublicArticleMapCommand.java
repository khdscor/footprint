package foot.footprint.domain.article.dto.articles;

import foot.footprint.domain.article.domain.LocationRange;

public class PublicArticleMapCommand extends ArticleMapCommand{

    public PublicArticleMapCommand(LocationRange locationRange) {
        super(locationRange);
    }
}