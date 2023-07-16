package foot.footprint.domain.group.dto.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String nickName;
    private String imageUrl;
}