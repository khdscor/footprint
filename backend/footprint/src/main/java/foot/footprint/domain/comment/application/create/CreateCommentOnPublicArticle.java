package foot.footprint.domain.comment.application.create;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.dao.CreateCommentRepository;
import foot.footprint.domain.comment.dto.CommentResponse;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.global.domain.AuthorDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("public")
public class CreateCommentOnPublicArticle extends CreateCommentServiceImpl{

    public CreateCommentOnPublicArticle(
        FindArticleRepository findArticleRepository,
        MemberRepository memberRepository,
        CreateCommentRepository createCommentRepository) {
        super(findArticleRepository, memberRepository, createCommentRepository);
    }

    @Override
    @Transactional
    public CommentResponse createComment(Long articleId, String content, Long memberId) {
        findAndValidateArticle(articleId);
        Member member = findAndValidateMember(memberId);
        return saveComment(articleId, content, AuthorDto.buildAuthorDto(member));
    }
}