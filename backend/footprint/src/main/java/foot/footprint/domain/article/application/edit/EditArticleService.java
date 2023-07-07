package foot.footprint.domain.article.application.edit;

public interface EditArticleService {

    public void edit(Long articleId, Long memberId, String newContent);
}