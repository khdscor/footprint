package foot.footprint.domain.comment.dto;

import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.global.domain.AuthorDto;
import java.util.Date;
import lombok.Getter;

@Getter
public class CommentResponse {

  private Long id;
  private String content;
  private AuthorDto author;
  private Date createDate;
  private Long totalLikes;

  public CommentResponse(Long id, String content, AuthorDto author, Date createDate) {
    this.id = id;
    this.content = content;
    this.author = author;
    this.createDate = createDate;
  }

  public CommentResponse(Long id, String content, Long userId, String nickName, String imageUrl,
      Date createDate, Long totalLikes) {
    this.id = id;
    this.content = content;
    this.author = new AuthorDto(userId, nickName, imageUrl);
    this.createDate = createDate;
    this.totalLikes = totalLikes;
  }

  public static CommentResponse toCommentResponse(Comment comment, AuthorDto authorDto) {
    return new CommentResponse(
        comment.getId(),
        comment.getContent(),
        authorDto,
        comment.getCreate_date()
    );
  }
}