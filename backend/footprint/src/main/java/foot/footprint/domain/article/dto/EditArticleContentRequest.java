package foot.footprint.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class EditArticleContentRequest {

    private String newContent;

    public EditArticleContentRequest(String newContent) {
        this.newContent = newContent;
    }

    public EditArticleContentRequest() {
    }
}