package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.util.ValidateIsMine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class ChangePrivateArticleLikeService extends ChangeArticleLikeServiceImpl {

    public ChangePrivateArticleLikeService(ArticleLikeRepository articleLikeRepository,
        FindArticleRepository findArticleRepository) {
        super(articleLikeRepository, findArticleRepository);
    }

    @Override
    @Transactional
    public void changeArticleLike(ArticleLikeDto articleLikeDto) {
        Article article = findAndValidateArticle(articleLikeDto.getArticleId());
        if (!article.isPrivate_map()) {
            throw new WrongMapTypeException("게시글이 개인지도에 포함되지 않습니다.");
        }
        ValidateIsMine.validateArticleIsMine(article.getMember_id(), articleLikeDto.getMemberId());
        changeLike(articleLikeDto);
    }
}