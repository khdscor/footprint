package foot.footprint.domain.user.exception;

public class NotExistsEmailException extends RuntimeException {

  public NotExistsEmailException(String message) {
    super(message);
  }
}
