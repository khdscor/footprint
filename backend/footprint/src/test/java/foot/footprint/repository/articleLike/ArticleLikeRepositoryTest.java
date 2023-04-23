package foot.footprint.repository.articleLike;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.article.dao.CreateArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.articleLike.dao.ArticleLikeRepository;
import foot.footprint.domain.articleLike.domain.ArticleLike;
import foot.footprint.domain.articleLike.dto.ArticleLikeDto;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleLikeRepositoryTest extends RepositoryTest {

  @Autowired
  private ArticleLikeRepository articleLikeRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void saveArticleLike() {
    //given
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    ArticleLike articleLike = createArticleLike(article.getId(), member.getId());

    //when
    articleLikeRepository.saveArticleLike(articleLike);

    //then
    assertThat(articleLike.getId()).isNotNull();
  }

  @Test
  public void existsMyLike() {
    //given
    ArticleLike articleLike = setUpArticleLike();
    ArticleLikeDto articleLikeDto = new ArticleLikeDto(articleLike.getArticle_id(),
        articleLike.getMember_id());
    ArticleLikeDto dummyDto = new ArticleLikeDto(100L,
        1L);

    //when & then
    assertThat(articleLikeRepository.existsMyLike(articleLikeDto)).isTrue();
    assertThat(articleLikeRepository.existsMyLike(dummyDto)).isFalse();
  }

  private ArticleLike createArticleLike(Long articleId, Long memebrId) {
    return ArticleLike.builder()
        .article_id(articleId)
        .member_id(memebrId).build();
  }

  private ArticleLike setUpArticleLike() {
    Member member = buildMember();
    memberRepository.saveMember(member);
    Article article = buildArticle(member.getId());
    createArticleRepository.saveArticle(article);
    ArticleLike articleLike = createArticleLike(article.getId(), member.getId());
    articleLikeRepository.saveArticleLike(articleLike);

    return articleLike;
  }
}