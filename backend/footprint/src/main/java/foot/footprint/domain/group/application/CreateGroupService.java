package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.dto.CreateGroupRequest;
import foot.footprint.domain.group.util.RandomStringGenerator;
import foot.footprint.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateGroupService {

  private final GroupRepository groupRepository;

  private final MemberGroupRepository memberGroupRepository;

  @Transactional
  public Long createGroup(CreateGroupRequest request, Long creatorId) {
    Group newGroup = saveGroup(request, creatorId);
    memberGroupRepository.saveMemberGroup(MemberGroup.createMemberGroup(newGroup));
    return newGroup.getId();
  }

  private Group saveGroup(CreateGroupRequest request, Long creatorId) {
    Group newGroup = new Group(request.getGroupName(), creatorId);
    groupRepository.saveGroup(newGroup);
    newGroup.addInvitationCode(generateInvitationCode(newGroup.getId()));
    groupRepository.saveGroup(newGroup);
    return newGroup;
  }

  private String generateInvitationCode(Long groupId) {
    return RandomStringGenerator.generate(5) + groupId;
  }
}