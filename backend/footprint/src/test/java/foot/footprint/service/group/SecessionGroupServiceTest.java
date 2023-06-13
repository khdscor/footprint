package foot.footprint.service.group;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.group.application.SecessionGroupService;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecessionGroupServiceTest {

    @Spy
    private GroupRepository groupRepository;

    @Mock
    private MemberGroupRepository memberGroupRepository;

    @InjectMocks
    private SecessionGroupService secessionGroupService;

    @Test
    @DisplayName("그룹 탈퇴 - 그룹 삭제 x")
    public void secessionGroup() {
        //given
        given(memberGroupRepository.deleteMemberGroup(any(), any()))
            .willReturn(1);
        given(memberGroupRepository.countMemberGroup(any()))
            .willReturn(1L);

        //when
        secessionGroupService.secessionGroup(1L, 1L);

        //then
        verify(groupRepository, times(0)).deleteById(any());
    }

    @Test
    @DisplayName("그룹 탈퇴 - 해당하는 그룹이 없거나 가입이 안되어있을 때")
    public void secessionGroup_IfNotExistsGroup() {
        //given
        given(memberGroupRepository.deleteMemberGroup(any(), any()))
            .willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> secessionGroupService.secessionGroup(1L, 1L))
            .isInstanceOf(NotExistsException.class);
    }

    @Test
    @DisplayName("그룹 탈퇴 - 탈퇴 후 그룹에 남은 인원이 없을 시")
    public void secessionGroup_IfMemberIsNotExists() {
        //given
        given(memberGroupRepository.deleteMemberGroup(any(), any()))
            .willReturn(1);
        given(memberGroupRepository.countMemberGroup(any()))
            .willReturn(0L);
        given(groupRepository.deleteById(any()))
            .willReturn(1);

        //when
        secessionGroupService.secessionGroup(1L, 1L);

        //then
        verify(groupRepository, times(1)).deleteById(any());
    }
}