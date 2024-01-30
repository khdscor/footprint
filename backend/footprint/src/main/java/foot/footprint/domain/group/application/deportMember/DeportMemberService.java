package foot.footprint.domain.group.application.deportMember;

import foot.footprint.domain.group.dto.DeportMemberCommand;

public interface DeportMemberService {

    void deport(DeportMemberCommand command);
}