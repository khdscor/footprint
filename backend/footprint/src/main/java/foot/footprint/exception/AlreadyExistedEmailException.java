package foot.footprint.exception;

public class AlreadyExistedEmailException extends RuntimeException {

    public AlreadyExistedEmailException(String message) {
        super(message);
    }
}
