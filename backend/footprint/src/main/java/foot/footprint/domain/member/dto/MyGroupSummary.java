package foot.footprint.domain.member.dto;

import foot.footprint.domain.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyGroupSummary {

    private Long groupId;
    private String name;
}