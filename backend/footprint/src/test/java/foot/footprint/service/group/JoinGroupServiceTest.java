package foot.footprint.service.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.group.application.join.JoinGeneralGroupService;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.exception.AlreadyJoinedException;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JoinGroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MemberGroupRepository memberGroupRepository;

    @InjectMocks
    private JoinGeneralGroupService joinGeneralGroupService;

    @Test
    @DisplayName("그룹 가입")
    public void join() {
        //given
        Group group = buildGroup();
        given(groupRepository.findByInvitationCode(any()))
            .willReturn(Optional.ofNullable(group));
        given(memberGroupRepository.checkAlreadyJoined(any(), any()))
            .willReturn(false);

        //when
        Long result = joinGeneralGroupService.join("test", group.getId());

        //then
        assertThat(result).isEqualTo(group.getId());
    }

    @Test
    @DisplayName("그룹 가입 - 이미 가입되어있는경우")
    public void join_IfAlreadyJoined() {
        //given
        Group group = buildGroup();
        given(groupRepository.findByInvitationCode(any()))
            .willReturn(Optional.ofNullable(group));
        given(memberGroupRepository.checkAlreadyJoined(any(), any()))
            .willReturn(true);

        //when & then
        assertThatThrownBy(
            () -> joinGeneralGroupService.join("test", group.getId()))
            .isInstanceOf(AlreadyJoinedException.class);
    }

    private Group buildGroup() {
        return Group.builder()
            .create_date(new Date())
            .name("test_group")
            .invitation_code("testCode")
            .owner_id(1L).build();
    }
}