package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeCommand;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.util.ObjectSerializer;
import foot.footprint.global.util.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class ChangePrivateArticleLikeService extends AbstractChangeArticleLikeService {

    public ChangePrivateArticleLikeService(ArticleLikeRepository articleLikeRepository,
        FindArticleRepository findArticleRepository, ObjectSerializer objectSerializer) {
        super(articleLikeRepository, findArticleRepository, objectSerializer);
    }

    @Override
    @Transactional
    public void changeArticleLike(ArticleLikeCommand articleLikeCommand) {
        Article article = findAndValidateArticle(articleLikeCommand.getArticleId());
        if (!article.isPrivate_map()) {
            throw new WrongMapTypeException("게시글이 개인지도에 포함되지 않습니다.");
        }
        Validate.validateArticleIsMine(article.getMember_id(), articleLikeCommand.getMemberId());
        changeLike(articleLikeCommand);
        updateRedis(articleLikeCommand);
    }
}