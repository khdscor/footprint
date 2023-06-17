package foot.footprint.global.util;

import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;

public class ValidateIsMine {

    public static void validateArticleIsMine(Long writerId, Long memberId) {
        if (writerId != memberId) {
            throw new NotMatchMemberException("해당글에 접근할 권한이 없습니다.");
        }
    }

    public static void validateInMyGroup(Long articleId, Long memberId,
        ArticleGroupRepository articleGroupRepository) {
        if (!articleGroupRepository.existsArticleInMyGroup(articleId, memberId)) {
            throw new NotAuthorizedOrExistException("해당글에 접근할 권한이 없습니다.");
        }
    }
}