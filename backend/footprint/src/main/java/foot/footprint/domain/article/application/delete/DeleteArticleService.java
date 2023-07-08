package foot.footprint.domain.article.application.delete;

public interface DeleteArticleService {

    void delete(Long articleId, Long memberId);
}