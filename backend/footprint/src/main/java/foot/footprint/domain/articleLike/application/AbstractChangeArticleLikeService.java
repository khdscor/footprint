package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ObjectSerializer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractChangeArticleLikeService implements ChangeArticleLikeService {

    protected final ArticleLikeRepository articleLikeRepository;
    protected final FindArticleRepository findArticleRepository;
    protected final ObjectSerializer objectSerializer;

    protected void changeLike(ArticleLikeCommand command) {
        if (command.isHasILiked()) {
            deleteLike(command);
            return;
        }
        articleLikeRepository.saveArticleLike(
            ArticleLike.createArticleLike(command.getMemberId(),
                command.getArticleId()));
    }

    private void deleteLike(ArticleLikeCommand command) {
        int deleted = articleLikeRepository.deleteArticleLike(command.getArticleId(),
            command.getMemberId());
        if (deleted == 0) {
            throw new NotExistsException("이미 좋아요를 취소하였거나 누르지 않았습니다.");
        }
    }

    protected Article findAndValidateArticle(Long articleId) {
        return findArticleRepository.findById(articleId)
            .orElseThrow(() -> new NotExistsException(" 해당 게시글이 존재하지 않습니다."));
    }

    protected void updateRedis(ArticleLikeCommand command) {
        String redisKey =
            "articleDetails::" + command.getArticleId();
        objectSerializer.updateArticleData(redisKey, ArticleUpdatePart.CHANGE_LIKE,
            command.isHasILiked());
    }
}