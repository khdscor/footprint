package foot.footprint.global.util;

import foot.footprint.domain.article.exception.NotMatchMemberException;

import java.util.Objects;

public class Validate {

    public static void validateArticleIsMine(Long writerId, Long memberId) {
        if (!Objects.equals(writerId, memberId)) {
            throw new NotMatchMemberException("해당글에 접근할 권한이 없습니다.");
        }
    }
}