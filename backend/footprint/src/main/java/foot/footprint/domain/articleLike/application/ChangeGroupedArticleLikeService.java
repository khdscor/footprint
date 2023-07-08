package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.util.ValidateIsMine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("grouped")
public class ChangeGroupedArticleLikeService extends ChangeArticleLikeServiceImpl {

    private final ArticleGroupRepository articleGroupRepository;

    public ChangeGroupedArticleLikeService(ArticleLikeRepository articleLikeRepository,
        FindArticleRepository findArticleRepository,
        ArticleGroupRepository articleGroupRepository) {
        super(articleLikeRepository, findArticleRepository);
        this.articleGroupRepository = articleGroupRepository;
    }

    @Override
    @Transactional
    public void changeArticleLike(ArticleLikeDto articleLikeDto) {
        findAndValidateArticle(articleLikeDto.getArticleId());
        ValidateIsMine.validateInMyGroup(articleLikeDto.getArticleId(),
            articleLikeDto.getMemberId(), articleGroupRepository);
        changeLike(articleLikeDto);
    }
}