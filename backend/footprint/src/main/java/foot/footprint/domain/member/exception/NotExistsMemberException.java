package foot.footprint.domain.member.exception;

public class NotExistsMemberException extends RuntimeException {

  public NotExistsMemberException(String message) {
    super(message);
  }
}