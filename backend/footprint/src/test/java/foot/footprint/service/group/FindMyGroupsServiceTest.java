package foot.footprint.service.group;

import foot.footprint.domain.group.application.findGroup.FindMyGroupsService;
import static org.assertj.core.api.Assertions.assertThat;
import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.dto.find.GroupSummaryResponse;
import foot.footprint.featureFactory.GroupFeatureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FindMyGroupsServiceTest {

    @Mock
    private MemberGroupRepository memberGroupRepository;

    @InjectMocks
    private FindMyGroupsService findMyGroupsService;

    @Test
    @DisplayName("그룹 조회 테스트")
    public void findGroups() {
        //given
        Long memberId = 1L;
        EasyRandom groupEasyRandom = GroupFeatureFactory.create(memberId);
        List<Group> groups =  IntStream.range(0, 100)
                .parallel()
                .mapToObj(i -> groupEasyRandom.nextObject(Group.class))
                .collect(Collectors.toList());
        given(memberGroupRepository.findMyGroups(any())).willReturn(groups);

        //when
        List<GroupSummaryResponse> responses = findMyGroupsService.findGroups(memberId);

        //then
        assertThat(responses.size()).isEqualTo(groups.size());
    }
}
