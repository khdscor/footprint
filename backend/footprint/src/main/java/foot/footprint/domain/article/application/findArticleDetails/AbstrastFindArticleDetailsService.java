package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageDto;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.article.dto.articleDetails.ArticlePrivateDetailsDto;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstrastFindArticleDetailsService implements FindArticleDetailsService {

    protected final FindArticleRepository findArticleRepository;

    protected final ArticleLikeRepository articleLikeRepository;

    protected final FindCommentRepository findCommentRepository;

    protected final CommentLikeRepository commentLikeRepository;

    protected final ObjectSerializer objectSerializer;

    protected Article findAndValidateArticle(Long articleId) {
        return findArticleRepository.findById(articleId)
            .orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
    }

    protected void addNonLoginInfo(Long articleId, ArticlePageResponse response) {
        ArticlePageDto dto = findArticleRepository.findArticleDetails(articleId)
            .orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
        response.addNonLoginInfo(dto.getArticleDetails(), dto.getComments());
    }

    protected void addLoginInfo(Long articleId, Long memberId, ArticlePageResponse response) {
        ArticlePageDto dto = findArticleRepository.findArticleDetails(articleId)
            .orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
        ArticlePrivateDetailsDto privateDto = findArticleRepository.findArticlePrivateDetails(
            articleId, memberId).orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
        response.addNonLoginInfo(dto.getArticleDetails(), dto.getComments());
        response.addLoginInfo(privateDto.isArticleLike(), privateDto.getCommentLikes(), memberId);
    }

    protected void validateMember(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthorizedOrExistException("해당 글에 접근할 권한이 없습니다.");
        }
    }
}