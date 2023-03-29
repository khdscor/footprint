package foot.footprint.domain.article.domain;

import foot.footprint.domain.article.dto.ArticleRangeRequest;
import lombok.Getter;

@Getter
public class LocationRange {

    private final double lowerLatitude;
    private final double upperLatitude;
    private final double lowerLongitude;
    private final double upperLongitude;

    public LocationRange(ArticleRangeRequest request) {
        this.lowerLatitude = request.getLatitude() - request.getLatitudeRange();
        this.upperLatitude = request.getLatitude() + request.getLatitudeRange();
        this.lowerLongitude = request.getLongitude() - request.getLongitudeRange();
        this.upperLongitude = request.getLongitude() + request.getLongitudeRange();
    }
}
