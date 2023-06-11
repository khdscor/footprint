package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindGroupService {

  private final MemberGroupRepository memberGroupRepository;

  @Transactional(readOnly = true)
  public List<GroupSummaryResponse> findMyImportantGroups(Long memberId) {
    return memberGroupRepository.findMyImportantGroups(memberId);
  }

  public List<GroupSummaryResponse> findMyGroups(Long memberId) {
    return memberGroupRepository.findMyGroups(memberId);
  }
}