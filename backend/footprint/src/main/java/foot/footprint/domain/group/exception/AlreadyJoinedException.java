package foot.footprint.domain.group.exception;

public class AlreadyJoinedException extends RuntimeException {

  public AlreadyJoinedException(String message) {
    super(message);
  }
}