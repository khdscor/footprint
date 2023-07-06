package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class ChangePublicArticleLikeService extends ChangeArticleLikeServiceImpl {

    public ChangePublicArticleLikeService(ArticleLikeRepository articleLikeRepository,
        FindArticleRepository findArticleRepository) {
        super(articleLikeRepository, findArticleRepository);
    }

    @Override
    @Transactional
    public void changeArticleLike(ArticleLikeDto articleLikeDto) {
        Article article = findArticle(articleLikeDto.getArticleId());
        if (!article.isPublic_map()) {
            throw new NumberFormatException("게시글이 전체지도에 포함되지 않습니다.");
        }
        changeLike(articleLikeDto);
    }
}