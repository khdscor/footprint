package foot.footprint.domain.group.application.findGroup;

import foot.footprint.domain.group.dto.GroupDetailsResponse;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import java.util.List;

public interface FindGroupService {

    List<GroupSummaryResponse> findMyImportantGroups(Long memberId);

    List<GroupSummaryResponse> findMyGroups(Long memberId);

    GroupDetailsResponse findOne(Long groupId, Long memberId);
}