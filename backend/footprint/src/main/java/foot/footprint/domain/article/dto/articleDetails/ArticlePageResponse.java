package foot.footprint.domain.article.dto.articleDetails;

import foot.footprint.domain.comment.dto.CommentResponse;

import foot.footprint.domain.comment.dto.CommentDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ArticlePageResponse {

    private ArticleDetails articleDetails;
    private boolean articleLike;
    private List<CommentResponse> comments;
    private List<Long> commentLikes = new ArrayList<>();
    private Long myMemberId;

    public ArticlePageResponse(ArticleDetails articleDetails, boolean articleLike,
        List<CommentResponse> comments, List<Long> commentLikes, Long myMemberId) {
        this.articleDetails = articleDetails;
        this.articleLike = articleLike;
        this.comments = comments;
        this.commentLikes = commentLikes;
        this.myMemberId = myMemberId;
    }

    public ArticlePageResponse() {
    }

    public void addNonLoginInfo(ArticleDetails articleDetails, List<CommentDto> comments) {
        this.articleDetails = articleDetails;
        this.comments = comments.stream().map(CommentResponse::toCommentResponse)
            .collect(Collectors.toList());
    }

    public void addLoginInfo(boolean articleLike, List<MyCommentLikesInArticle> commentLikes,
        Long myMemberId) {
        this.articleLike = articleLike;
        for (MyCommentLikesInArticle commentLike : commentLikes) {
            if (commentLike.hasMemberId() &&
                commentLike.getMemberId().equals(myMemberId)) {
                this.commentLikes.add(commentLike.getCommentId());
            }
        }
        this.myMemberId = myMemberId;
    }
}