package foot.footprint.domain.article.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleDto {

    private String title;
    private String content;
    private double latitude;    // 위도
    private double longitude;   // 경도
    private boolean publicMap;
    private boolean privateMap;
    private List<Long> groupIdsToBeIncluded;

    public static CreateArticleDto create(CreateArticleRequest request) {
        return CreateArticleDto.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .publicMap(request.isPublicMap())
            .privateMap(request.isPrivateMap())
            .groupIdsToBeIncluded(request.getGroupIdsToBeIncluded()).build();
    }

    public void updateGroupIdList(List<Long> groupIdsToBeIncluded) {
        this.groupIdsToBeIncluded = groupIdsToBeIncluded;
    }
}