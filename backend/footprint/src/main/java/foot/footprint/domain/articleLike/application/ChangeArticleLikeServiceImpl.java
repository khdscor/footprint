package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ChangeArticleLikeServiceImpl implements ChangeArticleLikeService {

    protected final ArticleLikeRepository articleLikeRepository;
    protected final FindArticleRepository findArticleRepository;

    protected void changeLike(ArticleLikeDto articleLikeDto) {
        if (articleLikeDto.isHasILiked()) {
            deleteLike(articleLikeDto);
            return;
        }
        articleLikeRepository.saveArticleLike(ArticleLike.createArticleLike(articleLikeDto));
    }

    private void deleteLike(ArticleLikeDto articleLikeDto) {
        int deleted = articleLikeRepository.deleteArticleLike(articleLikeDto);
        if (deleted == 0) {
            throw new NotExistsException("이미 좋아요를 취소하였거나 누르지 않았습니다.");
        }
    }

    protected Article findArticle(Long articleId) {
        return findArticleRepository.findById(articleId)
            .orElseThrow(() -> new NotExistsException(" 해당 게시글이 존재하지 않습니다."));
    }
}