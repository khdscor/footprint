package foot.footprint.repository.group;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.dto.GroupDetailsResponse;
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
        Group group = Group.builder().create_date(new Date()).name("test_group")
            .invitation_code("testCode").owner_id(1L).build();

        //when & then
        assertThat(group.getId()).isNull();
        groupRepository.saveGroup(group);

        //then
        assertThat(group.getId()).isNotNull();
    }

    @Test
    public void updateInvitationCode() {
        //given
        Group group = Group.builder().create_date(new Date()).name("test_group").owner_id(1L)
            .build();

        //when & then
        assertThat(group.getId()).isNull();
        groupRepository.saveGroup(group);
        group.addInvitationCode("testCode");
        groupRepository.updateInvitationCode(group);

        //then
        assertThat(group.getInvitation_code()).isNotNull();
    }

    @Test
    public void findByInvitationCode() {
        //given
        String invitationCode = "testCode";
        String anotherCode = "testOrCode";
        Group group = Group.builder().create_date(new Date()).name("test_group")
            .invitation_code(invitationCode).owner_id(1L).build();
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
        Group group = Group.builder().create_date(new Date()).name("test_group")
            .invitation_code(invitationCode).owner_id(1L).build();
        groupRepository.saveGroup(group);
        Member member = buildMember();
        memberRepository.saveMember(member);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);

        //when & then
        assertThat(groupRepository.findById(group.getId())).isPresent();
        assertThat(memberGroupRepository.findById(memberGroup.getId())).isPresent();
        int result = groupRepository.deleteById(group.getId());
        int dummy = groupRepository.deleteById(200L);
        assertThat(groupRepository.findById(group.getId())).isNotPresent();
        assertThat(memberGroupRepository.findById(memberGroup.getId())).isNotPresent();
        assertThat(result).isEqualTo(1);
        assertThat(dummy).isEqualTo(0);
    }

    @Test
    public void findAllByMemberId() {
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

    @Test
    public void changeGroupName() {
        //given
        Long memberId = 12L;
        String newName = "하하하하하하";
        Group group = buildGroup(memberId);
        groupRepository.saveGroup(group);

        //when
        int result1 = groupRepository.changeGroupName(group.getId(), 32L, newName);
        int result2 = groupRepository.changeGroupName(233L, memberId, newName);
        int result3 = groupRepository.changeGroupName(group.getId(), memberId, newName);

        //then
        Group editedGroup = groupRepository.findById(group.getId()).get();
        assertThat(result1).isEqualTo(0);
        assertThat(result2).isEqualTo(0);
        assertThat(result3).isEqualTo(1);
        assertThat(editedGroup.getName()).isEqualTo(newName);
    }

    @Test
    public void findGroupDetails() {
        //given
        Member member1 = buildMember();
        memberRepository.saveMember(member1);
        Member member2 = buildMember();
        memberRepository.saveMember(member2);
        Member member3 = buildMember();
        memberRepository.saveMember(member3);
        Group group = buildGroup(member1.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup1 = MemberGroup.createMemberGroup(group.getId(), member1.getId());
        MemberGroup memberGroup2 = MemberGroup.createMemberGroup(group.getId(), member2.getId());
        MemberGroup memberGroup3 = MemberGroup.createMemberGroup(group.getId(), member3.getId());
        memberGroup2.changeImportant();
        memberGroupRepository.saveMemberGroup(memberGroup1);
        memberGroupRepository.saveMemberGroup(memberGroup2);
        memberGroupRepository.saveMemberGroup(memberGroup3);

        //when & then
        Optional<GroupDetailsResponse> response1 = groupRepository.findGroupDetails(2L,
            member1.getId());
        assertThat(response1.isPresent()).isFalse();
        Optional<GroupDetailsResponse> response2 = groupRepository.findGroupDetails(group.getId(),
            32L);
        assertThat(response2.isPresent()).isFalse();
        Optional<GroupDetailsResponse> response3 = groupRepository.findGroupDetails(group.getId(),
            member1.getId());
        assertThat(response3.get().getMemberDetails()).hasSize(3);
        assertThat(response3.get().getImportant()).isFalse();
        Optional<GroupDetailsResponse> response4 = groupRepository.findGroupDetails(group.getId(),
            member2.getId());
        assertThat(response4.get().getMemberDetails()).hasSize(3);
        assertThat(response4.get().getImportant()).isTrue();
        assertThat(response4.get().getId()).isEqualTo(group.getId());
    }

    @Test
    public void findGroupName() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);

        //when
        Optional<String> name = groupRepository.findGroupName(member.getId(), group.getId());
        Optional<String> emptyName1 = groupRepository.findGroupName(member.getId(), 14L);
        Optional<String> emptyName2 = groupRepository.findGroupName(30L, group.getId());

        //then
        assertThat(name.isPresent()).isTrue();
        assertThat(name.get()).isEqualTo(group.getName());
        assertThat(emptyName1.isPresent()).isFalse();
        assertThat(emptyName2.isPresent()).isFalse();
    }

    private Group buildGroup(Long ownerId, String invitationCode) {
        return Group.builder().create_date(new Date()).name("test_group")
            .invitation_code(invitationCode).owner_id(ownerId).build();
    }
}