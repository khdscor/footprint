package foot.footprint.domain.article.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMapArticlesDto {

    List<ArticleMapResponse> responses;
    String groupName;
}
