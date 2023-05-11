package foot.footprint.domain.comment.application;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.comment.dto.AuthorDto;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCommentService {

  private final FindArticleRepository findArticleRepository;

  private final MemberRepository memberRepository;

  private final ArticleGroupRepository articleGroupRepository;

  private final CreateCommentRepository createCommentRepository;

  @Transactional
  public CommentResponse create(Long articleId, String content, Long memberId) {
    Article article = findArticleRepository.findById(articleId)
        .orElseThrow(() -> new NotExistsException(" 댓글을 작성하려는 게시글이 존재하지 않습니다."));
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new NotExistsException("해당하는 회원이 존재하지 않습니다."));
    if (article.isPublic_map()) {
      return createCommentOnPublicArticle(articleId, content, AuthorDto.buildAuthorDto(member));
    }
    if (article.isPrivate_map()) {
      return createCommentOnPrivateArticle(article, content, AuthorDto.buildAuthorDto(member));
    }
    return createCommentOnGroupedArticle(articleId, content, AuthorDto.buildAuthorDto(member));
  }

  private CommentResponse createCommentOnPublicArticle(Long articleId, String content,
      AuthorDto authorDto) {
    Comment comment = new Comment(content, new Date(), articleId, authorDto.getId());
    createCommentRepository.saveComment(comment);
    return CommentResponse.toCommentResponse(comment, authorDto);
  }

  private CommentResponse createCommentOnPrivateArticle(Article article, String content,
      AuthorDto authorDto) {
    validateIsMyArticle(article, authorDto.getId());
    Comment comment = new Comment(content, new Date(), article.getId(), authorDto.getId());
    createCommentRepository.saveComment(comment);
    return CommentResponse.toCommentResponse(comment, authorDto);
  }

  private CommentResponse createCommentOnGroupedArticle(Long articleId, String content,
      AuthorDto authorDto) {
    validateInMyGroup(articleId, authorDto.getId());
    Comment comment = new Comment(content, new Date(), articleId, authorDto.getId());
    createCommentRepository.saveComment(comment);
    return CommentResponse.toCommentResponse(comment, authorDto);
  }

  private void validateIsMyArticle(Article article, Long memberId) {
    if (article.getMember_id() != memberId) {
      throw new NotMatchMemberException("해당글에 좋아요할 권한이 없습니다.");
    }
  }

  private void validateInMyGroup(Long articleId, Long memberId) {
    if (!articleGroupRepository.existsArticleInMyGroup(articleId, memberId)) {
      throw new NotAuthorizedOrExistException("댓글을 작성할 권한이 없습니다.");
    }
  }
}