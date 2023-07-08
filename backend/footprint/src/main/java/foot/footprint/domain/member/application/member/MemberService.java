package foot.footprint.domain.member.application.member;

import foot.footprint.domain.member.dto.MemberImageResponse;
import foot.footprint.domain.member.dto.MyPageResponse;

public interface MemberService {

    MemberImageResponse findImageUrl(Long memberId);

    MyPageResponse findMyPageInfo(Long id);

    void withdraw(Long id);
}