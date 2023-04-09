package foot.footprint.global.error;

import foot.footprint.domain.article.exception.NotIncludedMapException;
import foot.footprint.domain.member.exception.AlreadyExistedEmailException;
import foot.footprint.domain.member.exception.NotMatchPasswordException;
import foot.footprint.global.error.exception.NotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NotIncludedMapException.class)
  public ResponseEntity<ErrorResponse> handleNotIncludedMapException(
      NotIncludedMapException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(AlreadyExistedEmailException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyExistedEmailException(
      AlreadyExistedEmailException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(NotExistsException.class)
  public ResponseEntity<ErrorResponse> NotExistsEmailException(
      NotExistsException e) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(NotMatchPasswordException.class)
  public ResponseEntity<ErrorResponse> handleNotMatchPasswordException(
      NotMatchPasswordException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e.getMessage()));
  }
}