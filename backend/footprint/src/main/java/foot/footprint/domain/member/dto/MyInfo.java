package foot.footprint.domain.member.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyInfo {

    private Long memberId;
    private String imageUrl;
    private String nickName;
    private String email;
    private Timestamp visitDate;
}