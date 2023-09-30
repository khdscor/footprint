package foot.footprint.domain.article.dto.articleDetails;



public enum ArticleUpdatePart {

    CHANGE_LIKE("change_like"){
        public<T> void apply(ArticlePageResponse response, T data){
            response.getArticleDetails().editContent((String) data);
        }
    },
    CHANGE_LIKE_COMMENT("change_like_comment"){
        public<T> void apply(ArticlePageResponse response, T data){

        }
    },
    EDIT_ARTICLE("edit_article"){
        public<T> void apply(ArticlePageResponse response, T data){

        }
    },
    EDIT_COMMENT("edit_comment"){
        public<T> void apply(ArticlePageResponse response, T data){

        }
    },
    ADD_COMMENT("add_comment"){
        public<T> void apply(ArticlePageResponse response, T data){

        }
    };

    ArticleUpdatePart(String updatePart) {
    }

    public abstract <T> void apply(ArticlePageResponse response, T data);
}
