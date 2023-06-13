package foot.footprint.domain.article.dto;

import foot.footprint.domain.comment.dto.CommentResponse;
import java.util.List;
import lombok.Getter;

@Getter
public class ArticlePageResponse {

    private ArticleDetailsDto articleDetails;
    private boolean articleLike;
    private List<CommentResponse> comments;
    private List<Long> commentLikes;
    private Long myMemberId;

    public ArticlePageResponse(ArticleDetailsDto articleDetails, boolean articleLike,
        List<CommentResponse> comments, List<Long> commentLikes, Long myMemberId) {
        this.articleDetails = articleDetails;
        this.articleLike = articleLike;
        this.comments = comments;
        this.commentLikes = commentLikes;
        this.myMemberId = myMemberId;
    }

    public ArticlePageResponse() {
    }

    public void addNonLoginInfo(ArticleDetailsDto articleDetails, List<CommentResponse> comments) {
        this.articleDetails = articleDetails;
        this.comments = comments;
    }

    public void addLoginInfo(boolean articleLike, List<Long> commentLikes, Long myMemberId) {
        this.articleLike = articleLike;
        this.commentLikes = commentLikes;
        this.myMemberId = myMemberId;
    }
}