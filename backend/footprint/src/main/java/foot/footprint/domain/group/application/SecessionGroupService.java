package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecessionGroupService {

  private final GroupRepository groupRepository;

  private final MemberGroupRepository memberGroupRepository;

  @Transactional
  public void secessionGroup(Long groupId, Long memberId) {
    deleteMemberGroup(groupId, memberId);
    deleteGroupIfMemberIsNotExist(groupId);
  }

  private void deleteMemberGroup(Long groupId, Long memberId) {
    int deleted = memberGroupRepository.deleteMemberGroup(groupId, memberId);
    if (deleted == 0) {
      throw new NotExistsException("이미 탈퇴한 그룹이거나 존재하지 않는 그룹입니다.");
    }
  }

  private void deleteGroupIfMemberIsNotExist(Long groupId) {
    Long memberCount = memberGroupRepository.countMemberGroup(groupId);
    if (memberCount <= 0) {
      groupRepository.deleteById(groupId);
    }
  }
}