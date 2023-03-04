package foot.footprint.util;

import lombok.Getter;

@Getter
public class LocationRange {

    private final double lowerLatitude;
    private final double upperLatitude;
    private final double lowerLongitude;
    private final double upperLongitude;

    public LocationRange(Double startLatitude, Double latitudeRange,
                         Double startLongitude, Double longitudeRange) {
        this.lowerLatitude = startLatitude - latitudeRange;
        this.upperLatitude = startLatitude + latitudeRange;
        this.lowerLongitude = startLongitude - longitudeRange;
        this.upperLongitude = startLongitude + longitudeRange;
    }
}
