package foot.footprint.domain.member.dto;

import foot.footprint.domain.group.dto.find.GroupSummaryResponse;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageResponse {

    private Long memberId;
    private String imageUrl;
    private String nickName;
    private String email;
    private Timestamp visitDate;
    List<MyArticleResponse> myArticles;
    List<GroupSummaryResponse> myGroups;
}