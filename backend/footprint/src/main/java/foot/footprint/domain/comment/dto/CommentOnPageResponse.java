package foot.footprint.domain.comment.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentOnPageResponse {

    private List<CommentResponse> comments;
    private Long cursorId;
    private boolean hasNextPage;

    public CommentOnPageResponse() {
    }

    public CommentOnPageResponse(List<CommentResponse> comments, Long cursorId) {
        this.comments = comments;
        this.cursorId = cursorId;
        hasNextPage= true;
    }

    public CommentOnPageResponse(List<CommentResponse> comments) {
        this.comments = comments;
        this.cursorId = -1L;
        hasNextPage= false;
    }
}