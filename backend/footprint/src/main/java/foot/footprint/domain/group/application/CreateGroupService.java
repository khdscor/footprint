package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateGroupService {

    private final GroupRepository groupRepository;

    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public Long createGroup(String groupName, Long creatorId) {
        Group newGroup = saveGroup(groupName, creatorId);
        memberGroupRepository.saveMemberGroup(MemberGroup.createMemberGroup(newGroup));
        return newGroup.getId();
    }

    private Group saveGroup(String groupName, Long creatorId) {
        Group newGroup = new Group(groupName, creatorId);
        groupRepository.saveGroup(newGroup);
        newGroup.addInvitationCode(generateInvitationCode(newGroup.getId()));
        groupRepository.updateInvitationCode(newGroup);
        return newGroup;
    }

    private String generateInvitationCode(Long groupId) {
        return RandomStringGenerator.generate(5) + groupId;
    }
}