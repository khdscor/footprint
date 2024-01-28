package foot.footprint.domain.comment.dto;

import foot.footprint.domain.article.dto.articleDetails.CommentsDto;
import foot.footprint.domain.comment.domain.Comment;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private Author author;
    private Date createDate;
    private Long totalLikes;

    public CommentResponse(Long id, String content, Long userId, String nickName, String imageUrl,
        Date createDate, Long totalLikes) {
        this.id = id;
        this.content = content;
        this.author = new Author(userId, nickName, imageUrl);
        this.createDate = createDate;
        this.totalLikes = totalLikes;
    }

    public static CommentResponse toCommentResponse(Comment comment, Author author) {
        return new CommentResponse(
            comment.getId(),
            comment.getContent(),
            author.getId(),
            author.getNickName(),
            author.getImageUrl(),
            comment.getCreate_date(),
            0L
        );
    }

    public static CommentResponse toCommentResponse(CommentsDto commentsDto) {
        return new CommentResponse(
            commentsDto.getCommentId(),
            commentsDto.getCommentContent(),
            commentsDto.getMemberId(),
            commentsDto.getNickName(),
            commentsDto.getImageUrl(),
            commentsDto.getCommentCreateDate(),
            commentsDto.getCommentTotalLikes()
        );
    }
}