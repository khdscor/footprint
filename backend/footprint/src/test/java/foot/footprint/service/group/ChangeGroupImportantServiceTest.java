package foot.footprint.service.group;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import foot.footprint.domain.group.application.changeImportant.ChangeMyGroupImportantService;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChangeGroupImportantServiceTest {

    @Mock
    private MemberGroupRepository memberGroupRepository;

    @InjectMocks
    private ChangeMyGroupImportantService changeMyGroupImportantService;

    @Test
    @DisplayName("즐겨찾기 변경 - 예외 발생시")
    public void changeImportant() {
        //given
        given(memberGroupRepository.changeImportant(any(), any()))
            .willReturn(0);

        //when & then
        assertThatThrownBy(
            () -> changeMyGroupImportantService.changeImportant(1L, 1L))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }
}