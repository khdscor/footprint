package foot.footprint.domain.articleLike.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.global.domain.MapType;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

  private final ArticleLikeRepository articleLikeRepository;

  private final FindArticleRepository findArticleRepository;

  @Transactional(readOnly = true)
  public boolean checkMyLike(ArticleLikeDto articleLikeDto) {
    boolean myLikeExists = articleLikeRepository.existsMyLike(articleLikeDto);
    return myLikeExists;
  }

  @Transactional
  public void changeArticleLike(ArticleLikeDto articleLikeDto, MapType mapType) {
    if (mapType == MapType.PUBLIC) {
      changePublicArticleLike(articleLikeDto);
    }
    if (mapType == MapType.PRIVATE) {
      changePrivateArticleLike(articleLikeDto);
    }
    if (mapType == MapType.GROUPED) {
      //changeGroupedArticleLike
    }
  }

  private void changePublicArticleLike(ArticleLikeDto articleLikeDto) {
    validateArticle(articleLikeDto);
    changeLike(articleLikeDto);
  }

  private void changePrivateArticleLike(ArticleLikeDto articleLikeDto) {
    validateMyArticle(articleLikeDto);
    changeLike(articleLikeDto);
  }

  private void validateMyArticle(ArticleLikeDto articleLikeDto) {
    Article article = validateArticle(articleLikeDto);
    articleLikeDto.validateArticleIsMine(article);
  }

  private Article validateArticle(ArticleLikeDto articleLikeDto) {
    return findArticleRepository.findById(articleLikeDto.getArticleId())
        .orElseThrow(() -> new NotExistsException("해당하는 게시글이 존재하지 않습니다."));
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
    System.out.println(deleted + " teststsetset");
    if (deleted == 0) {
      throw new NotExistsException("이미 좋아요를 취소하였거나 누르지 않았습니다.");
    }
  }
}