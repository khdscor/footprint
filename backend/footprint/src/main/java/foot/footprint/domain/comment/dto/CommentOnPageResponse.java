package foot.footprint.domain.comment.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CommentOnPageResponse {

    private final List<CommentResponse> comments;
    private final Long cursorId;
    private final boolean hasNextPage;

    public CommentOnPageResponse() {
        comments = new ArrayList<>();
        cursorId = -1L;
        hasNextPage = false;
    }

    public CommentOnPageResponse(List<CommentResponse> comments, Long cursorId) {
        this.comments = comments.subList(0, 10);
        this.cursorId = cursorId;
        hasNextPage = true;
    }

    public CommentOnPageResponse(List<CommentResponse> comments) {
        this.comments = comments;
        this.cursorId = -1L;
        hasNextPage = false;
    }
}