package foot.footprint.repository.group;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupRepositoryTest extends RepositoryTest {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberGroupRepository memberGroupRepository;

  @Test
  public void saveGroup() {
    //given
    Group group = Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code("testCode")
        .owner_id(1L).build();

    //when & then
    assertThat(group.getId()).isNull();
    groupRepository.saveGroup(group);

    //then
    assertThat(group.getId()).isNotNull();
  }

  @Test
  public void updateInvitationCode() {
    //given
    Group group = Group.builder()
        .create_date(new Date())
        .name("test_group")
        .owner_id(1L).build();

    //when & then
    assertThat(group.getId()).isNull();
    groupRepository.saveGroup(group);
    group.addInvitationCode("testCode");
    groupRepository.updateInvitationCode(group);

    //then
    assertThat(group.getInvitation_code()).isNotNull();
  }

  @Test
  public void findByInvitationCode(){
    //given
    String invitationCode = "testCode";
    String anotherCode = "testOrCode";
    Group group = Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code(invitationCode)
        .owner_id(1L).build();
    groupRepository.saveGroup(group);

    //when
    Optional<Group> foundGroup = groupRepository.findByInvitationCode(invitationCode);
    Optional<Group> anotherGroup = groupRepository.findByInvitationCode(anotherCode);
    //then
    assertThat(foundGroup).isPresent();
    assertThat(anotherGroup).isNotPresent();
  }

  @Test
  public void deleteById() {
    //given
    String invitationCode = "testCode";
    Group group = Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code(invitationCode)
        .owner_id(1L).build();
    groupRepository.saveGroup(group);

    //when & then
    assertThat(groupRepository.findById(group.getId())).isPresent();
    int result = groupRepository.deleteById(group.getId());
    int dummy = groupRepository.deleteById(200L);
    assertThat(groupRepository.findById(group.getId())).isNotPresent();
    assertThat(result).isEqualTo(1);
    assertThat(dummy).isEqualTo(0);
  }

  @Test
  public void findAllByMemberId(){
    //given
    Member member = buildMember();
    memberRepository.saveMember(member);
    Group group1 = buildGroup(member.getId(), "testGroup1");
    Group group2 = buildGroup(member.getId(), "testGroup2");
    groupRepository.saveGroup(group1);
    groupRepository.saveGroup(group2);
    MemberGroup memberGroup1 = MemberGroup.createMemberGroup(group1.getId(), member.getId());
    MemberGroup memberGroup2 = MemberGroup.createMemberGroup(group2.getId(), member.getId());
    memberGroupRepository.saveMemberGroup(memberGroup1);
    memberGroupRepository.saveMemberGroup(memberGroup2);

    //when
    List<Long> groupIds = groupRepository.findAllByMemberId(member.getId());

    //then
    assertThat(groupIds.size()).isEqualTo(2);
  }

  private Group buildGroup(Long ownerId, String invitationCode) {
    return Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code(invitationCode)
        .owner_id(ownerId).build();
  }
}