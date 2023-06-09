package foot.footprint.domain.group.application;

import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeportMemberService {

  private final MemberGroupRepository memberGroupRepository;

  private final GroupRepository groupRepository;

  @Transactional
  public void deport(Long groupId, Long memberId, Long myId) {
    validateGroupIsMine(groupId, myId, memberId);
    int deleted = memberGroupRepository.deleteMemberGroup(groupId, memberId);
    if (deleted == 0) {
      throw new NotExistsException("해당인원이 그룹에 이미 존재하지 않습니다.");
    }
  }

  private void validateGroupIsMine(Long groupId, Long myId, Long memberId) {
    if (memberId == myId) {
      throw new NotMatchMemberException("자기 자신을 탈퇴할 수 없습니다.");
    }
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new NotExistsException("해당하는 그룹이 존재하지 않습니다."));
    if (!Objects.equals(group.getOwner_id(), myId)) {
      throw new NotMatchMemberException("그룹원을 추방시킬 권한이 없습니다.");
    }
  }
}