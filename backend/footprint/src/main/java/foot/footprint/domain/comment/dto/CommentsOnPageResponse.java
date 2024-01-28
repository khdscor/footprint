package foot.footprint.domain.comment.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommentsOnPageResponse {

    private final List<CommentResponse> comments;
    private final Long cursorId;
    private final boolean hasNextPage;

    public CommentsOnPageResponse() {
        comments = new ArrayList<>();
        cursorId = -1L;
        hasNextPage = false;
    }

    //10개의 댓글만을 반환하고, 다음 페이지가 존재하는지 여부를 반환한다.
    public CommentsOnPageResponse(List<CommentDto> comments, Long cursorId) {
        this.comments = toCommentResponseList(comments).subList(0, 10);
        this.cursorId = cursorId;
        hasNextPage = true;
    }

    public CommentsOnPageResponse(List<CommentDto> comments) {
        this.comments = toCommentResponseList(comments);
        this.cursorId = -1L;
        hasNextPage = false;
    }

    private List<CommentResponse> toCommentResponseList(List<CommentDto> comments) {
      return  comments.stream()
          .map(CommentResponse::toCommentResponse)
          .collect(Collectors.toList());
    }
}