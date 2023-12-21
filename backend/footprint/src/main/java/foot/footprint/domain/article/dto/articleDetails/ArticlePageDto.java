package foot.footprint.domain.article.dto.articleDetails;

import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CommentUpdateDto;
import foot.footprint.domain.comment.dto.CommentsDto;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageDto {
    private Long articleId;
    private ArticleDetailsDto articleDetails;
    private List<CommentsDto> comments;

    public void changeLike(boolean hasLiked) {
        Long changeNum = hasLiked ? -1L : 1L;
        articleDetails.updateTotalLikes(changeNum);
    }

    public void addComment(CommentsDto comment) {
        comments.add(0, comment);
    }
//
//    public void removeComment(Long commentId) {
//        for (int i = 0; i < comments.size(); i++) {
//            if (comments.get(i).getId().equals(commentId)) {
//                comments.remove(i);
//                break;
//            }
//        }
//    }
//
//    public void changeCommentLike(Long commentId) {
//        Long num = -1L;
//        if (!commentLikes.remove(commentId)) {
//            commentLikes.add(commentId);
//            num = -num;
//        }
//        for (CommentResponse comment : comments) {
//            if (comment.getId().equals(commentId)) {
//                comment.updateTotalLikes(num);
//                break;
//            }
//        }
//    }
//
    public void changeCommentContent(CommentUpdateDto dto) {
        for (CommentsDto comment : comments) {
            if (comment.getCommentId().equals(dto.getId())) {
                comment.editContent(dto.getNewContent());
                break;
            }
        }
    }
}
