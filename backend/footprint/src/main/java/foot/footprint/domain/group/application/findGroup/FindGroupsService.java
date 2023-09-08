package foot.footprint.domain.group.application.findGroup;

import foot.footprint.domain.group.dto.find.GroupSummaryResponse;

import java.util.List;

public interface FindGroupsService {

    List<GroupSummaryResponse> findGroups(Long memberId);
}