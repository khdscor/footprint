package foot.footprint.domain.user.exception;

public class AlreadyExistedEmailException extends RuntimeException {

    public AlreadyExistedEmailException(String message) {
        super(message);
    }
}
