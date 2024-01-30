package foot.footprint.domain.article.dto.articleDetails;

import foot.footprint.domain.comment.dto.CommentUpdateDto;
import foot.footprint.domain.comment.dto.CommentDto;
import foot.footprint.domain.commentLike.dto.ChangeTotalLikesDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageDto {

    private Long articleId;
    private ArticleDetails articleDetails;
    private List<CommentDto> comments;

    public void changeLike(boolean hasLiked) {
        Long changeNum = hasLiked ? -1L : 1L;
        articleDetails.updateTotalLikes(changeNum);
    }

    public void addComment(CommentDto comment) {
        comments.add(0, comment);
    }

    public void removeComment(Long commentId) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getCommentId().equals(commentId)) {
                comments.remove(i);
                break;
            }
        }
    }

    public void changeCommentLike(ChangeTotalLikesDto dto) {
        for (CommentDto comment : comments) {
            if (comment.getCommentId().equals(dto.getCommentId())) {
                Long num = dto.isHasLiked() ? -1L : 1L;
                comment.updateTotalLikes(num);
                break;
            }
        }
    }

    public void changeCommentContent(CommentUpdateDto dto) {
        for (CommentDto comment : comments) {
            if (comment.getCommentId().equals(dto.getId())) {
                comment.editContent(dto.getNewContent());
                break;
            }
        }
    }
}