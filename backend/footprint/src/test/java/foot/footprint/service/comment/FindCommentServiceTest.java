package foot.footprint.service.comment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.dao.FindArticleRepository;
import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.comment.application.FindCommentService;
import foot.footprint.domain.comment.dao.FindCommentRepository;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindCommentServiceTest {

  @Mock
  private FindArticleRepository findArticleRepository;

  @Mock
  private FindCommentRepository findCommentRepository;

  @Mock
  private ArticleGroupRepository articleGroupRepository;

  @InjectMocks
  private FindCommentService findCommentService;

  @Test
  public void findComments() {
    //given
    Long memberId = 1L;
    given(findArticleRepository.findById(any())).willReturn(
        Optional.ofNullable(createArticle(memberId, false, true)));
  }

  private Article createArticle(Long memberId, boolean privateMap, boolean publicMap) {
    return Article.builder().id(1L).content("ddddd").latitude(10.1).longitude(10.1)
        .private_map(privateMap).public_map(publicMap).title("히히히히").member_id(memberId).build();
  }
}
