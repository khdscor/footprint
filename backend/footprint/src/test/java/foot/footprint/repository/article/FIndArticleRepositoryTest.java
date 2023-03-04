package foot.footprint.repository.article;

import foot.footprint.domain.Article;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FIndArticleRepositoryTest extends RepositoryTest{
    @Autowired
    private findArticleRepository findArticleRepository;
    @Test
    public void findArticleRepository(){
        List<Article> articles = findArticleRepository.findPublicMapArticles(
                20.0,
                0.0,
                20.0,
                0.0
        );
        assertThat(articles).hasSize(1);
    }
}
