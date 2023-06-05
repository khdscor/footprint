package foot.footprint.domain.article.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.ArticleDetailsDto;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.commentLike.dao.CommentLikeRepository;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.security.user.CustomUserDetails;
import foot.footprint.global.util.ValidateIsMine;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindArticleDetailsService {

  private final FindArticleRepository findArticleRepository;

  private final ArticleLikeRepository articleLikeRepository;

  private final FindCommentRepository findCommentRepository;

  private final CommentLikeRepository commentLikeRepository;

  private final ArticleGroupRepository articleGroupRepository;

  @Transactional(readOnly = true)
  public ArticlePageResponse findDetails(Long articleId, CustomUserDetails userDetails) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException(" 댓글을 작성하려는 게시글이 존재하지 않습니다."));
    ArticlePageResponse response = new ArticlePageResponse();
    return findArticleResponse(article, userDetails, response);
  }

  private ArticlePageResponse findArticleResponse(Article article, CustomUserDetails userDetails,
      ArticlePageResponse response) {
    if (article.isPublic_map() == true) {
      return findPublicArticle(article.getId(), userDetails, response);
    }
    if (article.isPrivate_map() == true) {
      return findPrivateArticle(article, userDetails, response);
    }
    return findGroupedArticle(article.getId(), userDetails, response);
  }

  private ArticlePageResponse findPublicArticle(Long articleId, CustomUserDetails userDetails,
      ArticlePageResponse response) {
    addNonLoginInfo(articleId, response);
    if (userDetails == null) {
      response.addLoginInfo(false, null, null);
      return response;
    }
    addLoginInfo(articleId, userDetails.getId(), response);
    return response;
  }

  private ArticlePageResponse findPrivateArticle(Article article, CustomUserDetails userDetails,
      ArticlePageResponse response) {
    if (userDetails == null) {
      throw new NotAuthorizedOrExistException("해당 글에 접근할 권한이 없습니다.");
    }
    ValidateIsMine.validateArticleIsMine(article.getMember_id(), userDetails.getId());
    addNonLoginInfo(article.getId(), response);
    addLoginInfo(article.getId(), userDetails.getId(), response);
    return response;
  }

  private ArticlePageResponse findGroupedArticle(Long articleId, CustomUserDetails userDetails,
      ArticlePageResponse response) {
    if (userDetails == null) {
      throw new NotAuthorizedOrExistException("해당 글에 접근할 권한이 없습니다.");
    }
    ValidateIsMine.validateInMyGroup(articleId, userDetails.getId(), articleGroupRepository);
    addNonLoginInfo(articleId, response);
    addLoginInfo(articleId, userDetails.getId(), response);
    return response;
  }

  private ArticlePageResponse addNonLoginInfo(Long articleId, ArticlePageResponse response) {
    ArticleDetailsDto details = findArticleRepository.findArticleDetails(articleId);
    List<CommentResponse> comments = findCommentRepository.findAllByArticleId(articleId);
    response.addNonLoginInfo(details, comments);
    return response;
  }

  private ArticlePageResponse addLoginInfo(Long articleId, Long memberId,
      ArticlePageResponse response) {
    Boolean myArticleLikeExists = articleLikeRepository.existsMyLike(
        new ArticleLikeDto(articleId, memberId));
    List<Long> commentLikes = commentLikeRepository.findCommentIdsILiked(articleId, memberId);
    response.addLoginInfo(myArticleLikeExists, commentLikes, memberId);
    return response;
  }
}