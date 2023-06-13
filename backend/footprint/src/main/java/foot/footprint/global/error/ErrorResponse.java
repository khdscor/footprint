package foot.footprint.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
