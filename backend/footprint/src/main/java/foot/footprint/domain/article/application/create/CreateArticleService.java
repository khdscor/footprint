package foot.footprint.domain.article.application.create;

import foot.footprint.domain.article.dto.CreateArticleCommand;

public interface CreateArticleService {

    Long create(CreateArticleCommand command);
}