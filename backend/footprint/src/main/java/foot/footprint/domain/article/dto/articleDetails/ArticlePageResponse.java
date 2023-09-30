package foot.footprint.domain.article.dto.articleDetails;

import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CommentsDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ArticlePageResponse {

    private ArticleDetails articleDetails;
    private boolean articleLike;
    private List<CommentResponse> comments;
    private List<Long> commentLikes;
    private Long myMemberId;

    public ArticlePageResponse(ArticleDetailsDto articleDetails, boolean articleLike,
        List<CommentResponse> comments, List<Long> commentLikes, Long myMemberId) {
        this.articleDetails = ArticleDetails.toArticleDetails(articleDetails);
        this.articleLike = articleLike;
        this.comments = comments;
        this.commentLikes = commentLikes;
        this.myMemberId = myMemberId;
    }

    public ArticlePageResponse() {
    }

    public void addNonLoginInfo(ArticleDetailsDto articleDetails, List<CommentsDto> comments) {
        this.articleDetails = ArticleDetails.toArticleDetails(articleDetails);
        this.comments = comments.stream().map(CommentResponse::toCommentResponse)
            .collect(Collectors.toList());
    }

    public void addLoginInfo(boolean articleLike, List<Long> commentLikes, Long myMemberId) {
        this.articleLike = articleLike;
        this.commentLikes = commentLikes;
        this.myMemberId = myMemberId;
    }

    public void changeLike() {
        Long changeNum = articleLike ? -1L : 1L;
        articleDetails.updateTotalLikes(changeNum);
        articleLike = !articleLike;
    }

    public void addComment(CommentResponse comment) {
        comments.add(0, comment);
    }

    public void changeCommentLike(Long commentId) {
        Long num = -1L;
        if (!commentLikes.remove(commentId)) {
            commentLikes.add(commentId);
            num = -num;
        }
        for (CommentResponse comment : comments) {
            if (comment.getId().equals(commentId)) {
                comment.updateTotalLikes(num);
                break;
            }
        }
    }
}