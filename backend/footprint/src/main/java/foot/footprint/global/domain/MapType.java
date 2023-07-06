package foot.footprint.global.domain;

import foot.footprint.global.error.exception.WrongMapTypeException;
import java.util.Arrays;

public enum MapType {
    PUBLIC("public"), PRIVATE("private"), GROUPED("grouped");

    private final String name;

    MapType(String name) {
        this.name = name;
    }

    public static MapType from(String mapType) {
        return Arrays.stream(values()).filter(value -> value.name.equalsIgnoreCase(mapType))
            .findFirst()
            .orElseThrow(() -> new WrongMapTypeException(mapType + "에 해당하는 MapType이 존재하지 않습니다."));
    }
}