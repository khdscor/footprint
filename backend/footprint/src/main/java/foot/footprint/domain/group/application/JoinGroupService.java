package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.exception.AlreadyJoinedException;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinGroupService {

    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public Long join(String invitationCode, Long memberId) {
        Group group = findGroup(invitationCode);
        validateHasJoined(group, memberId);
        saveMemberGroup(group, memberId);
        return group.getId();
    }

    private Group findGroup(String invitationCode) {
        return groupRepository.findByInvitationCode(invitationCode)
            .orElseThrow(() -> new NotExistsException("존재하지 않는 초대코드입니다."));
    }

    private void validateHasJoined(Group group, Long memberId) {
        boolean isPresent = memberGroupRepository.checkAlreadyJoined(group.getId(), memberId);
        if (isPresent) {
            throw new AlreadyJoinedException("이미 가입된 그룹입니다.");
        }
    }

    private void saveMemberGroup(Group group, Long memberId) {
        memberGroupRepository.saveMemberGroup(
            MemberGroup.createMemberGroup(group.getId(), memberId));
    }
}