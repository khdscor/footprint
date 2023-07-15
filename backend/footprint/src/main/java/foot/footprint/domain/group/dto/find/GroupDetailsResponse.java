package foot.footprint.domain.group.dto.find;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailsResponse {

    private GroupInfo groupInfo;
    private List<MemberDto> memberDetails;

    public static GroupDetailsResponse toResponse(Long memberId, GroupDetailsDto dto) {
        GroupInfo groupInfo = GroupInfo.builder()
            .id(dto.getId())
            .name(dto.getName())
            .isOwner(Objects.equals(memberId, dto.getOwnerId()))
            .invitationCode(dto.getInvitationCode())
            .important(dto.getImportant())
            .createDate(dto.getCreateDate()).build();
        return GroupDetailsResponse.builder()
            .groupInfo(groupInfo)
            .memberDetails(dto.getMemberDetails()).build();
    }
}