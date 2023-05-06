package foot.footprint.domain.group.domain;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class ArticleGroup {

  private Long id;
  private Date create_date;
  private Long article_id;
  private Long group_id;

  public ArticleGroup(Long id, Date create_date, Long article_id, Long group_id) {
    this.id = id;
    this.create_date = create_date;
    this.article_id = article_id;
    this.group_id = group_id;
  }

  public static ArticleGroup createArticleGroup(Long groupId, Long articleId) {
    return ArticleGroup.builder()
        .create_date(new Date())
        .group_id(groupId)
        .article_id(articleId).build();
  }
}