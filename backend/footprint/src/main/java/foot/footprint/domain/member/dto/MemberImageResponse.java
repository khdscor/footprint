package foot.footprint.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberImageResponse {

    private Long memberId;
    private String imageUrl;
}