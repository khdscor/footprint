package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.global.domain.AuthorDto;
import foot.footprint.global.util.ValidateIsMine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("private")
public class CreateCommentOnPrivateArticle extends CreateCommentServiceImpl {

    public CreateCommentOnPrivateArticle(FindArticleRepository findArticleRepository,
        MemberRepository memberRepository, CreateCommentRepository createCommentRepository) {
        super(findArticleRepository, memberRepository, createCommentRepository);
    }

    @Override
    @Transactional
    public CommentResponse createComment(Long articleId, String content, Long memberId) {
        Article article = findAndValidateArticle(articleId);
        Member member = findAndValidateMember(memberId);
        AuthorDto authorDto = AuthorDto.buildAuthorDto(member);
        ValidateIsMine.validateArticleIsMine(article.getMember_id(), authorDto.getId());
        return saveComment(articleId, content, authorDto);
    }
}