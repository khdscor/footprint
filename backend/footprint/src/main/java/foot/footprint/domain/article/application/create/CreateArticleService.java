package foot.footprint.domain.article.application.create;

import foot.footprint.domain.article.dto.CreateArticleDto;

public interface CreateArticleService {

    Long create(CreateArticleDto request, Long memberId);
}