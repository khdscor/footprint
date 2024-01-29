package foot.footprint.domain.member.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageResponse {

    private Long memberId;
    private MyInfo myInfo;
    private List<MyArticleResponse> myArticles;
    private List<MyGroupSummary> myGroups;
}