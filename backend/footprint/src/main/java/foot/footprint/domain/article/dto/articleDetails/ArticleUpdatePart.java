package foot.footprint.domain.article.dto.articleDetails;


import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CommentUpdateDto;

public enum ArticleUpdatePart {

    CHANGE_LIKE("change_like") {
        public <T> void apply(ArticlePageDto dto, T data) {
            dto.changeLike((boolean) data);
        }
    },
    CHANGE_COMMENT_LIKE("change_comment_like") {
        public <T> void apply(ArticlePageDto dto, T data) {
//            response.changeCommentLike((Long) data);
        }
    },
    EDIT_ARTICLE("edit_article") {
        public <T> void apply(ArticlePageDto dto, T data) {
//            response.getArticleDetails().editContent((String) data);
        }
    },
    EDIT_COMMENT("edit_comment") {
        public <T> void apply(ArticlePageDto dto, T data) {
//            response.changeCommentContent((CommentUpdateDto) data);
        }
    },
    ADD_COMMENT("add_comment") {
        public <T> void apply(ArticlePageDto dto, T data) {
//            response.addComment((CommentResponse) data);
        }
    },
    REMOVE_COMMENT("remove_comment") {
        public <T> void apply(ArticlePageDto dto, T data) {
//            response.removeComment((Long) data);
        }
    };

    ArticleUpdatePart(String updatePart) {
    }

    public abstract <T> void apply(ArticlePageDto dto, T data);
}