package foot.footprint.domain.article.application.create;

import foot.footprint.domain.article.dto.CreateArticleRequest;

public interface CreateArticleService {

    public Long create(CreateArticleRequest request, Long memberId);
}