package foot.footprint.domain.article.dto.articleDetails;


import foot.footprint.domain.comment.dto.CommentUpdateDto;

public enum ArticleUpdatePart {

    CHANGE_LIKE("change_like") {
        public <T> void apply(ArticlePageResponse response, T data) {
            response.getArticleDetails().editContent((String) data);
        }
    },
    CHANGE_LIKE_COMMENT("change_like_comment") {
        public <T> void apply(ArticlePageResponse response, T data) {

        }
    },
    EDIT_ARTICLE("edit_article") {
        public <T> void apply(ArticlePageResponse response, T data) {
            response.getArticleDetails().editContent((String) data);
        }
    },
    EDIT_COMMENT("edit_comment") {
        public <T> void apply(ArticlePageResponse response, T data) {
            CommentUpdateDto dto = (CommentUpdateDto) data;
            for (int i = 0; i < response.getComments().size(); i++) {
                if (response.getComments().get(i).getId().equals(dto.getId())) {
                    response.getComments().get(i).editContent(dto.getNewContent());
                    break;
                }
            }
        }
    },
    ADD_COMMENT("add_comment") {
        public <T> void apply(ArticlePageResponse response, T data) {

        }
    };

    ArticleUpdatePart(String updatePart) {
    }

    public abstract <T> void apply(ArticlePageResponse response, T data);
}
