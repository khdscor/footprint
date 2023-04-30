package foot.footprint.repository.group;

import static org.assertj.core.api.Assertions.assertThat;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.repository.RepositoryTest;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupRepositoryTest extends RepositoryTest {

  @Autowired
  private GroupRepository groupRepository;

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
}