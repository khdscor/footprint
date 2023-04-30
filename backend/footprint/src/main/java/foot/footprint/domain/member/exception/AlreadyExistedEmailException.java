package foot.footprint.domain.member.exception;

public class AlreadyExistedEmailException extends RuntimeException {

  public AlreadyExistedEmailException(String message) {
    super(message);
  }
}