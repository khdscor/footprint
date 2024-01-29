package foot.footprint.domain.group.application.findGroup;

import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.dto.find.GroupSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("groups")
@RequiredArgsConstructor
public class FindMyGroupsService implements FindGroupsService {

    private final MemberGroupRepository memberGroupRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GroupSummaryResponse> findGroups(Long memberId) {
        List<Group> groups = memberGroupRepository.findMyGroups(memberId);
        return groups.stream()
                .map(GroupSummaryResponse::new)
                .collect(Collectors.toList());
    }
}