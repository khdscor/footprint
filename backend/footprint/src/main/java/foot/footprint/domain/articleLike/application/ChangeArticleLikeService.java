package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;

public interface ChangeArticleLikeService {

    void changeArticleLike(ArticleLikeCommand articleLikeCommand);
}