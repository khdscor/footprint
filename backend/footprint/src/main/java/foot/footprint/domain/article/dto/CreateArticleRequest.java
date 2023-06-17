package foot.footprint.domain.article.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import java.util.List;

@Getter
public class CreateArticleRequest {

    @NotEmpty(message = "제목을 입력해주세요.")
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "제목의 최대길이를 초과하였습니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private double latitude;    // 위도

    private double longitude;   // 경도
    private boolean publicMap;
    private boolean privateMap;
    private List<Long> groupIdsToBeIncluded;

    public CreateArticleRequest(String title, String content, double latitude, double longitude,
        boolean publicMap, boolean privateMap, List<Long> groupIdsToBeIncluded) {
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.publicMap = publicMap;
        this.privateMap = privateMap;
        this.groupIdsToBeIncluded = groupIdsToBeIncluded;
    }

    public void updateGroupIdList(List<Long> groupIdsToBeIncluded) {
        this.groupIdsToBeIncluded = groupIdsToBeIncluded;
    }
}