package foot.footprint.domain.article.dto.articles;

import foot.footprint.domain.article.domain.LocationRange;
import lombok.Getter;

@Getter
public class GroupedArticleMapCommand extends ArticleMapCommand{

    private final Long memberId;
    private final Long groupId;

    public GroupedArticleMapCommand(LocationRange locationRange, Long memberId, Long groupId) {
        super(locationRange);
        this.memberId = memberId;
        this.groupId = groupId;
    }
}