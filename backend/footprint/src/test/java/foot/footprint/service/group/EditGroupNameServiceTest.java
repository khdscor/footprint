package foot.footprint.service.group;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.group.application.edit.EditGroupNameServiceImpl;
import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EditGroupNameServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private EditGroupNameServiceImpl editGroupNameService;

    @Test
    @DisplayName("예외 발생 테스트")
    public void change() {
        //given
        given(groupRepository.changeGroupName(any(), any(), any())).willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> editGroupNameService.change(any(), any(), any()))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }
}
