package foot.footprint.service.group;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.group.application.CreateGroupService;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dto.CreateGroupRequest;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateGroupServiceTest {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private CreateGroupService createGroupService;

  @Test
  public void createGroup() {
    //given
    Member creator = buildMember();
    CreateGroupRequest request = new CreateGroupRequest("name");

    //when
    Long groupId = createGroupService.createGroup(request, creator.getId());

    //then
    assertThat(groupId).isNotNull();
    assertThat(groupRepository.findById(groupId).get().getInvitation_code()).isNotNull();
  }

  private Member buildMember() {
    Member member = Member.builder()
        .id(20L)
        .email("test")
        .image_url(null)
        .provider_id("test")
        .provider(AuthProvider.google)
        .nick_name("tset")
        .role(Role.USER)
        .join_date(new Date())
        .password("password").build();
    memberRepository.saveMember(member);
    return member;
  }
}