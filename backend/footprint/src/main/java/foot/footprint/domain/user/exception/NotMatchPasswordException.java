package foot.footprint.domain.user.exception;

public class NotMatchPasswordException extends RuntimeException {

  public NotMatchPasswordException(String message) {
    super(message);
  }
}
