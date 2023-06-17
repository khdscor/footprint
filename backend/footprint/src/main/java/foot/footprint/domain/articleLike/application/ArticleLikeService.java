package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ValidateIsMine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;

    private final FindArticleRepository findArticleRepository;

    private final ArticleGroupRepository articleGroupRepository;

    @Transactional
    public void changeArticleLike(ArticleLikeDto articleLikeDto) {
        Article article = findArticleRepository.findById(articleLikeDto.getArticleId())
            .orElseThrow(() -> new NotExistsException(" 댓글을 작성하려는 게시글이 존재하지 않습니다."));
        if (article.isPublic_map()) {
            changePublicArticleLike(articleLikeDto);
            return;
        }
        if (article.isPrivate_map()) {
            changePrivateArticleLike(articleLikeDto, article.getMember_id());
            return;
        }
        changeGroupedArticleLike(articleLikeDto);
    }

    private void changePublicArticleLike(ArticleLikeDto articleLikeDto) {
        changeLike(articleLikeDto);
    }

    private void changePrivateArticleLike(ArticleLikeDto articleLikeDto, Long writerId) {
        ValidateIsMine.validateArticleIsMine(writerId, articleLikeDto.getMemberId());
        changeLike(articleLikeDto);
    }

    private void changeGroupedArticleLike(ArticleLikeDto articleLikeDto) {
        ValidateIsMine.validateInMyGroup(articleLikeDto.getArticleId(),
            articleLikeDto.getMemberId(),
            articleGroupRepository);
        changeLike(articleLikeDto);
    }

    private void changeLike(ArticleLikeDto articleLikeDto) {
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
}