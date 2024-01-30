package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.comment.dto.CreateCommentCommand;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.comment.dto.Author;
import foot.footprint.global.error.exception.WrongMapTypeException;
import foot.footprint.global.util.ObjectSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class CreateCommentOnPublicArticle extends AbstractCreateCommentService {

    public CreateCommentOnPublicArticle(
        FindArticleRepository findArticleRepository,
        MemberRepository memberRepository,
        CreateCommentRepository createCommentRepository,
        ObjectSerializer objectSerializer) {
        super(findArticleRepository, memberRepository, createCommentRepository, objectSerializer);
    }

    @Override
    @Transactional
    public CommentResponse createComment(CreateCommentCommand command) {
        Article article = findAndValidateArticle(command.getArticleId());
        if (!article.isPublic_map()) {
            throw new WrongMapTypeException("게시글이 전체지도에 포함되지 않습니다.");
        }
        Member member = findAndValidateMember(command.getMemberId());
        CommentResponse response = saveComment(command.getArticleId(), command.getContent(), Author.buildAuthor(member));
        updateRedis(command.getArticleId(), response);
        return response;
    }
}