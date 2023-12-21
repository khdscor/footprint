package foot.footprint.domain.article.dto.articleDetails;

import foot.footprint.domain.comment.dto.CommentUpdateDto;
import foot.footprint.domain.comment.dto.CommentsDto;
import foot.footprint.domain.commentLike.dto.ChangeTotalLikesDto;

public enum ArticleUpdatePart {

    CHANGE_LIKE("change_like") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.changeLike((boolean) data);
        }
    },
    CHANGE_COMMENT_LIKE("change_comment_like") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.changeCommentLike((ChangeTotalLikesDto) data);
        }
    },
    EDIT_ARTICLE("edit_article") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.getArticleDetails().editContent((String) data);
        }
    },
    EDIT_COMMENT("edit_comment") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.changeCommentContent((CommentUpdateDto) data);
        }
    },
    ADD_COMMENT("add_comment") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.addComment((CommentsDto) data);
        }
    },
    REMOVE_COMMENT("remove_comment") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.removeComment((Long) data);
        }
    };

    ArticleUpdatePart(String updatePart) {
    }

    public abstract <T> void apply(ArticlePageDto dto, T data);
}