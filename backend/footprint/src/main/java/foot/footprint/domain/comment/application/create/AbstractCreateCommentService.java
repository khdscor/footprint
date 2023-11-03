package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.article.dto.articleDetails.ArticleUpdatePart;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.domain.Comment;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.global.domain.AuthorDto;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ObjectSerializer;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCreateCommentService implements CreateCommentService {

    protected final FindArticleRepository findArticleRepository;
    protected final MemberRepository memberRepository;
    protected final CreateCommentRepository createCommentRepository;
    private final ObjectSerializer objectSerializer;

    protected CommentResponse saveComment(Long articleId, String content, AuthorDto authorDto) {
        Comment comment = new Comment(content, new Date(), articleId, authorDto.getId());
        createCommentRepository.saveComment(comment);
        return CommentResponse.toCommentResponse(comment, authorDto);
    }

    protected Article findAndValidateArticle(Long articleId) {
        return findArticleRepository.findById(articleId)
            .orElseThrow(() -> new NotExistsException(" 댓글을 작성하려는 게시글이 존재하지 않습니다."));
    }

    protected Member findAndValidateMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new NotExistsException("해당하는 회원이 존재하지 않습니다."));
    }

    protected void updateRedis (Long articleId, Long memberId, CommentResponse response) {
        String redisKey = "articleDetails::" + articleId + ":" + memberId;
        objectSerializer.updateArticleData(redisKey, ArticleUpdatePart.ADD_COMMENT, response);
    }
}