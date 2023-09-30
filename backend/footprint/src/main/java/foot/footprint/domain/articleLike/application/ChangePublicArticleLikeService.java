package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.domain.comment.dto.CommentUpdateDto;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.util.ObjectSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class ChangePublicArticleLikeService extends AbstractChangeArticleLikeService {

    public ChangePublicArticleLikeService(ArticleLikeRepository articleLikeRepository,
        FindArticleRepository findArticleRepository, ObjectSerializer objectSerializer) {
        super(articleLikeRepository, findArticleRepository, objectSerializer);
    }

    @Override
    @Transactional
    public void changeArticleLike(ArticleLikeDto articleLikeDto) {
        Article article = findAndValidateArticle(articleLikeDto.getArticleId());
        if (!article.isPublic_map()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
        changeLike(articleLikeDto);
        updateRedis(articleLikeDto.getArticleId());
    }
}