package foot.footprint.domain.member.exception;

public class NotExistsEmailException extends RuntimeException {

  public NotExistsEmailException(String message) {
    super(message);
  }
}
