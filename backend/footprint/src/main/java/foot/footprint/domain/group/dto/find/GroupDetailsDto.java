package foot.footprint.domain.group.dto.find;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailsDto {

    private Long id;
    private String name;
    private Long ownerId;
    private String invitationCode;
    private Boolean important;
    private Date createDate;
    private List<MemberDto> memberDetails;
}
