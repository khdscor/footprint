package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.dto.GroupDetailsResponse;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindGroupService {

    private final MemberGroupRepository memberGroupRepository;

    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public List<GroupSummaryResponse> findMyImportantGroups(Long memberId) {
        return memberGroupRepository.findMyImportantGroups(memberId);
    }

    public List<GroupSummaryResponse> findMyGroups(Long memberId) {
        return memberGroupRepository.findMyGroups(memberId);
    }

    public GroupDetailsResponse findOne(Long groupId, Long memberId) {
        GroupDetailsResponse response = groupRepository.findGroupDetails(groupId, memberId)
            .orElseThrow(() -> new NotAuthorizedOrExistException("그룹을 조회할 권한이 없습니다."));
        return response;
    }
}