package foot.footprint.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupSummaryResponse {

    private Long groupId;
    private String name;
}