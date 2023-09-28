package foot.footprint.domain.article.application.edit;

import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.dto.ArticlePageResponse;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.global.util.ObjectSerializer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditGeneralArticleService implements EditArticleService{

    private final EditArticleRepository editArticleRepository;

    private final ObjectSerializer objectSerializer;

    @Override
    @Transactional
    public void edit(Long articleId, Long memberId, String newContent) {
        int result = editArticleRepository.editArticle(articleId, memberId, newContent);
        if (result == 0) {
            throw new NotMatchMemberException("글이 존재하지 않거나 수정할 권한이 없습니다.");
        }

        String redisKey = "articleDetails::" + articleId;
        Optional<ArticlePageResponse> cache = objectSerializer.getData(redisKey,
            ArticlePageResponse.class);
        if (cache.isPresent()) {
            cache.get().getArticleDetails().editContent(newContent);
            objectSerializer.saveData(redisKey, cache.get(), 1);
        }
    }
}