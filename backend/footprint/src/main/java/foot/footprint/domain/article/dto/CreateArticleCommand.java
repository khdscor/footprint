package foot.footprint.domain.article.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleCommand {

    private String title;
    private String content;
    private Long memberId;
    private double latitude;    // 위도
    private double longitude;   // 경도
    private boolean publicMap;
    private boolean privateMap;
    private Set<Long> groupIdsToBeIncluded;

    public static CreateArticleCommand create(CreateArticleRequest request, Long memberId) {
        return CreateArticleCommand.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .memberId(memberId)
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .publicMap(request.isPublicMap())
            .privateMap(request.isPrivateMap())
            .groupIdsToBeIncluded(request.getGroupIdsToBeIncluded()).build();
    }

    public void updateGroupIdList(Set<Long> groupIdsToBeIncluded) {
        this.groupIdsToBeIncluded = groupIdsToBeIncluded;
    }
}