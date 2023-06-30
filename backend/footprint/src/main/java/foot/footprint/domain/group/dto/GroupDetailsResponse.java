package foot.footprint.domain.group.dto;

import java.util.Date;
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

    private Long id;
    private String name;
    private boolean isOwner;
    private String invitationCode;
    private Boolean important;
    private Date createDate;
    private List<MemberDto> memberDetails;

    public static GroupDetailsResponse toResponse(Long memberId, GroupDetailsDto dto) {
        return GroupDetailsResponse.builder()
            .id(dto.getId())
            .name(dto.getName())
            .isOwner(Objects.equals(memberId, dto.getOwnerId()))
            .invitationCode(dto.getInvitationCode())
            .important(dto.getImportant())
            .createDate(dto.getCreateDate())
            .memberDetails(dto.getMemberDetails()).build();
    }
}