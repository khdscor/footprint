package foot.footprint.global.error.exception;

public class WrongAccessRedisException extends RuntimeException {

    public WrongAccessRedisException(String message) {
        super(message);
    }
}
