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
  public void checkAlreadyJoined(){
    //given
    Member member = buildMember();
    memberRepository.saveMember(member);
    Group group = buildGroup(member.getId());
    groupRepository.saveGroup(group);
    MemberGroup memberGroup =  buildMemberGroup(group.getId(), member.getId());
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
    MemberGroup memberGroup =  buildMemberGroup(group.getId(), member.getId());
    memberGroupRepository.saveMemberGroup(memberGroup);


    //when
    boolean result1 = memberGroupRepository.checkAlreadyJoined(group.getId(), member.getId());
    int deleted = memberGroupRepository.deleteMemberGroup(group.getId(), member.getId());
    boolean result2 = memberGroupRepository.checkAlreadyJoined(group.getId(), member.getId());

    //then
    assertThat(result1).isEqualTo(true);
    assertThat(deleted).isEqualTo(1);
    assertThat(result2).isEqualTo(false);
  }

  private Group buildGroup(Long ownerId) {
    return Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code("testCode")
        .owner_id(ownerId).build();
  }

  private MemberGroup buildMemberGroup(Long groupId, Long memberId){
    return MemberGroup.builder()
        .create_date(new Date())
        .group_id(groupId)
        .member_id(memberId)
        .important(true).build();
  }
}