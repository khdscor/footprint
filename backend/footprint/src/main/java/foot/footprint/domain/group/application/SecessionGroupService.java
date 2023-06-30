package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecessionGroupService {

    private final GroupRepository groupRepository;

    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public void secessionGroup(Long groupId, Long memberId) {
        Group group = findGroup(groupId);
        if (group.getOwner_id().equals(memberId)) {
            checkRemainingMember(groupId, memberId);
            return;
        }
        deleteMemberGroup(groupId, memberId);
    }

    private Group findGroup(Long groupId) {
        return groupRepository.findById(groupId)
            .orElseThrow(() -> new NotExistsException("해당되는 그룹이 존재하지 않습니다."));
    }

    private void checkRemainingMember(Long groupId, Long memberId) {
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByGroupId(groupId);
        //owner 만 그룹에 남아있을 경우
        if (memberGroups.size() == 1) {
            groupRepository.deleteById(groupId);
            log.info(groupId + " 그룹이 삭제되었습니다.");
            return;
        }
        updateOwner(groupId, memberId, memberGroups);
    }

    private void updateOwner(Long groupId, Long memberId, List<MemberGroup> memberGroups) {
        MemberGroup nextMemberGroup = findNextOwner(memberGroups, memberId);
        Long nextOwnerId = nextMemberGroup.getMember_id();
        groupRepository.updateOwner(groupId, nextOwnerId);
        deleteMemberGroup(groupId, memberId);
    }

    private MemberGroup findNextOwner(List<MemberGroup> memberGroups, Long memberId) {
        return memberGroups.stream()
            .filter(memberGroup -> !Objects.equals(memberGroup.getMember_id(), memberId))
            .findFirst().orElseThrow(() -> new NotExistsException("해당되는 회원이 존재하지 않습니다."));
    }

    private void deleteMemberGroup(Long groupId, Long memberId) {
        int deleted = memberGroupRepository.deleteMemberGroup(groupId, memberId);
        if (deleted == 0) {
            throw new NotExistsException("이미 탈퇴한 그룹이거나 존재하지 않는 그룹입니다.");
        }
    }
}