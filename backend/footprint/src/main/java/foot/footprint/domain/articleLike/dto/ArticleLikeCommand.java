package foot.footprint.domain.articleLike.dto;

import lombok.Getter;

@Getter
public class ArticleLikeCommand {

    private final Long articleId;
    private final Long memberId;
    private final boolean hasILiked;

    public ArticleLikeCommand(Long articleId, Long memberId, boolean hasILiked) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.hasILiked = hasILiked;
    }

    public ArticleLikeCommand(Long articleId, Long memberId) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.hasILiked = true;
    }
}