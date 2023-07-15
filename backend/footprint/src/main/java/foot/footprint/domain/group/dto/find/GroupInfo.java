package foot.footprint.domain.group.dto.find;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfo {
    private Long id;
    private String name;
    private boolean isOwner;
    private String invitationCode;
    private Boolean important;
    private Date createDate;
}