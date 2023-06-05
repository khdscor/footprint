package foot.footprint.domain.article.dto;

import foot.footprint.domain.articleLike.dto.ArticleLikeResponse;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.commentLike.dto.CommentLikeDto;
import foot.footprint.domain.member.dto.MyIdResponse;
import java.util.List;

public class ArticlePageResponse {

  private ArticleDetailsDto articleDetailsDto;
  private ArticleLikeResponse articleLike;
  private List<CommentResponse> responses;
  private List<CommentLikeDto> likes;
  private MyIdResponse myId;

  public ArticlePageResponse(ArticleDetailsDto articleDetailsDto, ArticleLikeResponse articleLike,
      List<CommentResponse> responses, List<CommentLikeDto> likes, MyIdResponse myId) {
    this.articleDetailsDto = articleDetailsDto;
    this.articleLike = articleLike;
    this.responses = responses;
    this.likes = likes;
    this.myId = myId;
  }
}