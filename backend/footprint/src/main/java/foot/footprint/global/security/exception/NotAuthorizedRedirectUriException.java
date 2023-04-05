package foot.footprint.global.security.exception;

public class NotAuthorizedRedirectUriException extends RuntimeException {

  public NotAuthorizedRedirectUriException(String message) {
    super(message);
  }
}