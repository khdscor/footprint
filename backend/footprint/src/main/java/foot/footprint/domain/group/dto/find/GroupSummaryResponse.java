package foot.footprint.domain.group.dto.find;

import foot.footprint.domain.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSummaryResponse {

    private Long groupId;
    private String name;

    public GroupSummaryResponse(Group group) {
        this.groupId = group.getId();
        this.name = group.getName();
    }
}