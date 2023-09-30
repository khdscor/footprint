package foot.footprint.domain.group.dto.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSummaryResponse {

    private Long groupId;
    private String name;
}