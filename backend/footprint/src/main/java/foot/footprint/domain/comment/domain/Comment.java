package foot.footprint.domain.comment.domain;

import foot.footprint.global.error.exception.WrongInputException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class Comment {

  private Long id;

  private String content;

  private Date create_date;

  private Long article_id;

  private Long member_id;

  public Comment(Long id, String content, Date create_date, Long article_id, Long member_id) {
    this.id = id;
    validate(content);
    this.content = content;
    this.create_date = create_date;
    this.article_id = article_id;
    this.member_id = member_id;
  }

  private void validate(String content) {
    if (Objects.isNull(content) || content.trim().isEmpty()) {
      throw new WrongInputException("내용이 비어있습니다.");
    }
  }
}