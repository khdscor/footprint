package foot.footprint.domain.group.application.findGroup;

import foot.footprint.domain.group.dto.find.GroupDetailsResponse;
import foot.footprint.domain.group.dto.find.GroupNameResponse;
import foot.footprint.domain.group.dto.find.GroupSummaryResponse;

import java.util.List;

public interface FindGroupService {

    GroupDetailsResponse findOne(Long groupId, Long memberId);

    GroupNameResponse findName(Long groupId, Long memberId);
}