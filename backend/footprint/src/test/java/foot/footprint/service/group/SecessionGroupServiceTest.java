package foot.footprint.service.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.group.application.SecessionGroupService;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecessionGroupServiceTest {

    @Spy
    private GroupRepository groupRepository;

    @Spy
    private MemberGroupRepository memberGroupRepository;

    @InjectMocks
    private SecessionGroupService secessionGroupService;

    @Test
    @DisplayName("그룹 탈퇴 - 그룹의 주인이 아닐경우")
    public void secessionGroupIfNotOwner() {
        //given
        Long groupId = 30L;
        Long ownerId = 1L;
        Long accessMemberId = 2L;
        Group group = SetUpMethods.buildGroup(ownerId);
        given(groupRepository.findById(any())).willReturn(Optional.ofNullable(group));
        given(memberGroupRepository.deleteMemberGroup(any(), any())).willReturn(1);

        //when
        secessionGroupService.secessionGroup(groupId, accessMemberId);

        //then
        verify(memberGroupRepository, times(0)).findAllByGroupId(any());
    }

    @Test
    @DisplayName("그룹 탈퇴 - 그룹의 주인이고 남은 그룹원이 존재할 경우")
    public void secessionGroupIfOwner() {
        //given
        Long groupId = 30L;
        Long ownerId = 1L;
        Long secondMemberId = 2L;
        Group group = SetUpMethods.buildGroup(ownerId);
        List<MemberGroup> memberGroups = new ArrayList<>();
        memberGroups.add(SetUpMethods.buildMemberGroup(groupId, ownerId));
        memberGroups.add(SetUpMethods.buildMemberGroup(groupId, secondMemberId));
        given(groupRepository.findById(any())).willReturn(Optional.ofNullable(group));
        given(memberGroupRepository.findAllByGroupId(any())).willReturn(memberGroups);
        given(memberGroupRepository.deleteMemberGroup(any(), any())).willReturn(1);
        given(groupRepository.updateOwner(any(), any())).willReturn(1);

        //when
        secessionGroupService.secessionGroup(groupId, ownerId);

        //then
        verify(groupRepository, times(0)).deleteById(any());
        verify(groupRepository, times(1)).updateOwner(any(), any());
        verify(memberGroupRepository, times(1)).deleteMemberGroup(any(), any());
    }

    @Test
    @DisplayName("그룹 탈퇴 - 그룹의 주인이고 혼자 남았을 경우")
    public void secessionGroupIfOwnerRemainAlone() {
        //given
        Long groupId = 30L;
        Long ownerId = 1L;
        Group group = SetUpMethods.buildGroup(ownerId);
        List<MemberGroup> memberGroups = new ArrayList<>();
        memberGroups.add(SetUpMethods.buildMemberGroup(groupId, ownerId));
        given(groupRepository.findById(any())).willReturn(Optional.ofNullable(group));
        given(memberGroupRepository.findAllByGroupId(any())).willReturn(memberGroups);
        given(groupRepository.deleteById(any())).willReturn(1);

        //when
        secessionGroupService.secessionGroup(groupId, ownerId);

        //then
        verify(groupRepository, times(1)).deleteById(any());
        verify(memberGroupRepository, times(0)).deleteMemberGroup(any(), any());
    }
}