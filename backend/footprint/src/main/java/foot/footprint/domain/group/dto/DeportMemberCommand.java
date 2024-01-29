package foot.footprint.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeportMemberCommand {
    private final Long groupId;
    private final Long targetMemberId;
    private final Long myId;
}