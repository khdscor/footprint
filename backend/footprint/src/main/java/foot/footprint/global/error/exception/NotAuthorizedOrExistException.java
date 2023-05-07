package foot.footprint.global.error.exception;

public class NotAuthorizedOrExistException extends RuntimeException{
  public NotAuthorizedOrExistException(String message) {
    super(message);
  }
}