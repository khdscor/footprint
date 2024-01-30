package foot.footprint.domain.article.application.findArticleDetails;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageDto;
import foot.footprint.domain.article.dto.articleDetails.ArticlePageResponse;
import foot.footprint.domain.article.dto.articleDetails.ArticlePagePrivateDetailsDto;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ObjectSerializer;
import java.util.Optional;
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

    protected void addNonLoginInfo(ArticlePageResponse response, Long articleId) {
        String redisKey = "articleDetails::" + articleId;
        Optional<ArticlePageDto> cache = objectSerializer.getData(redisKey, ArticlePageDto.class);
        // redis에 데이터가 있을 경우 - DB 접근 x
        if (cache.isPresent()) {
            response.addNonLoginInfo(cache.get().getArticleDetails(), cache.get().getComments());
            objectSerializer.saveData(redisKey, cache.get(), 60);
            return;
        }
        // redis에 데이터가 없을 경우 - DB 접근 o
        ArticlePageDto dto = findArticleRepository.findArticleDetails(articleId)
            .orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
        response.addNonLoginInfo(dto.getArticleDetails(), dto.getComments());
        //redis에 저장
        objectSerializer.saveData(redisKey, dto, 60);
    }

    protected void addLoginInfo(Long articleId, Long memberId, ArticlePageResponse response) {
        ArticlePagePrivateDetailsDto privateDto = findArticleRepository.findArticlePrivateDetails(
            articleId, memberId).orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
        response.addLoginInfo(privateDto.isArticleLike(), privateDto.getCommentLikes(), memberId);
    }

    protected void validateMember(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthorizedOrExistException("해당 글에 접근할 권한이 없습니다.");
        }
    }
}