package foot.footprint.repository.group;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MeemberGroupRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void saveMemberGroup() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = MemberGroup.builder()
            .create_date(new Date())
            .group_id(group.getId())
            .member_id(member.getId())
            .important(true).build();

        //when & then
        assertThat(memberGroup.getId()).isNull();
        memberGroupRepository.saveMemberGroup(memberGroup);

        //then
        assertThat(memberGroup.getId()).isNotNull();
    }

    @Test
    public void checkAlreadyJoined() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);

        //when
        boolean result = memberGroupRepository.checkAlreadyJoined(group.getId(), member.getId());
        boolean result2 = memberGroupRepository.checkAlreadyJoined(200L, 100L);

        //then
        assertThat(result).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    public void deleteMemberGroup() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);

        //when
        boolean result1 = memberGroupRepository.checkAlreadyJoined(group.getId(), member.getId());
        int deleted = memberGroupRepository.deleteMemberGroup(group.getId(), member.getId());
        boolean result2 = memberGroupRepository.checkAlreadyJoined(group.getId(), member.getId());
        int dummyDeleted = memberGroupRepository.deleteMemberGroup(group.getId(), 1000L);

        //then
        assertThat(result1).isEqualTo(true);
        assertThat(deleted).isEqualTo(1);
        assertThat(result2).isEqualTo(false);
        assertThat(dummyDeleted).isEqualTo(0);
    }

    @Test
    public void countMemberGroup() {
        //given
        Member member = buildMember();
        Member member2 = buildMember();
        memberRepository.saveMember(member);
        memberRepository.saveMember(member2);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);
        MemberGroup memberGroup2 = buildMemberGroup(group.getId(), member2.getId());
        memberGroupRepository.saveMemberGroup(memberGroup2);

        //when
        Long count = memberGroupRepository.countMemberGroup(group.getId());
        memberGroupRepository.deleteMemberGroup(group.getId(), member.getId());
        memberGroupRepository.deleteMemberGroup(group.getId(), member2.getId());
        Long count2 = memberGroupRepository.countMemberGroup(group.getId());

        //then
        assertThat(count).isEqualTo(2);
        assertThat(count2).isEqualTo(0);
    }

    @Test
    public void changeImportant() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group = buildGroup(member.getId());
        groupRepository.saveGroup(group);
        MemberGroup memberGroup = buildMemberGroup(group.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup);

        //when & then
        assertThat(memberGroup.isImportant()).isTrue();
        memberGroupRepository.changeImportant(group.getId(), member.getId());
        Optional<MemberGroup> changedMemberGroup = memberGroupRepository.findById(member.getId());
        assertThat(changedMemberGroup.get().isImportant()).isFalse();
        memberGroupRepository.changeImportant(group.getId(), member.getId());
        Optional<MemberGroup> changedMemberGroup2 = memberGroupRepository.findById(member.getId());
        assertThat(changedMemberGroup2.get().isImportant()).isTrue();
    }

    @Test
    public void findMyImportantGroups() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group1 = buildGroup(member.getId());
        groupRepository.saveGroup(group1);
        MemberGroup memberGroup1 = buildMemberGroup(group1.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup1);

        //when
        List<GroupSummaryResponse> responses = memberGroupRepository.findMyImportantGroups(
            member.getId());

        //then
        assertThat(responses).hasSize(1);
    }

    @Test
    public void findMyGroups() {
        //given
        Member member = buildMember();
        memberRepository.saveMember(member);
        Group group1 = buildGroup(member.getId());
        groupRepository.saveGroup(group1);
        MemberGroup memberGroup1 = buildMemberGroup(group1.getId(), member.getId());
        memberGroupRepository.saveMemberGroup(memberGroup1);

        //when
        List<GroupSummaryResponse> responses = memberGroupRepository.findMyGroups(member.getId());

        //then
        assertThat(responses).hasSize(1);
    }
}