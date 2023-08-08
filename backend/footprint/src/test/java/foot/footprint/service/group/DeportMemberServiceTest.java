package foot.footprint.service.group;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.article.dao.EditArticleRepository;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.group.application.deportMember.DeportMemberServiceImpl;
import foot.footprint.domain.group.dao.ArticleGroupRepository;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeportMemberServiceTest {

    @Mock
    private MemberGroupRepository memberGroupRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private EditArticleRepository editArticleRepository;

    @Mock
    private ArticleGroupRepository articleGroupRepository;

    @InjectMocks
    private DeportMemberServiceImpl deportMemberServiceImpl;

    @Test
    @DisplayName("그룹원 추방 - 그룹의 주인이 아닐 시")
    public void deport_IfGroupIsNotMine() {
        //given
        Long ownerId = 12L;
        Group group = buildGroup(1000L);
        given(groupRepository.findById(any())).willReturn(Optional.ofNullable(group));

        //when & then
        assertThatThrownBy(
            () -> deportMemberServiceImpl.deport(1L, 1L, ownerId))
            .isInstanceOf(NotMatchMemberException.class);
    }

    @Test
    @DisplayName("그룹원 추방 - 그룹원이 존재하지 않을 시")
    public void deport_IfMemberNotExists() {
        //given
        Long ownerId = 12L;
        Group group = buildGroup(ownerId);
        given(groupRepository.findById(any())).willReturn(Optional.ofNullable(group));
        given(memberGroupRepository.deleteMemberGroup(any(), any())).willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> deportMemberServiceImpl.deport(1L, 1L, ownerId))
            .isInstanceOf(NotExistsException.class);
    }

    private Group buildGroup(Long ownerId) {
        return Group.builder().create_date(new Date()).name("test_group")
            .invitation_code("testCode")
            .owner_id(ownerId).build();
    }
}