package foot.footprint.global.error;

import foot.footprint.domain.article.exception.NotIncludedMapException;
import foot.footprint.domain.article.exception.NotMatchMemberException;
import foot.footprint.domain.group.exception.AlreadyJoinedException;
import foot.footprint.domain.member.exception.AlreadyExistedEmailException;
import foot.footprint.domain.member.exception.NotMatchPasswordException;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.error.exception.WrongInputException;
import foot.footprint.global.error.exception.WrongMapTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NotIncludedMapException.class)
  public ResponseEntity<ErrorResponse> handleNotIncludedMapException(NotIncludedMapException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(AlreadyExistedEmailException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyExistedEmailException(
      AlreadyExistedEmailException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(NotExistsException.class)
  public ResponseEntity<ErrorResponse> NotExistsEmailException(NotExistsException e) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(NotMatchPasswordException.class)
  public ResponseEntity<ErrorResponse> handleNotMatchPasswordException(
      NotMatchPasswordException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(NotMatchMemberException.class)
  public ResponseEntity<ErrorResponse> handleNotMatchMemberException(NotMatchMemberException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(WrongMapTypeException.class)
  public ResponseEntity<ErrorResponse> handleWrongMapTypeException(WrongMapTypeException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(WrongInputException.class)
  public ResponseEntity<ErrorResponse> handleWrongInputException(WrongInputException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(AlreadyJoinedException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyJoinedException(AlreadyJoinedException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(NotAuthorizedOrExistException.class)
  public ResponseEntity<ErrorResponse> handleNotAuthorizedOrExistException(
      NotAuthorizedOrExistException e) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponse(e.getMessage()));
  }

  //아래 4가지는 기본적으로 설정된 에러
  //Post시 body가 비어있거나 형식이 알맞지 않을 때
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("요청사항중 일부 값이 잘못 설정되었습니다."));
  }

  //Post시 body내의 argument와 controller에서 설정한 requestParam 내의 요소가 불일치
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("요청사항중 일부 값이 잘못 설정되었습니다."));
  }

  //요청시 queryString 값을 잘못입력하거나 입력안함
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("요청사항중 일부 값이 잘못 설정되었습니다."));
  }

  //요청시 queryString의 타입이 잘못됨
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("요청사항중 일부 값이 잘못 설정되었습니다."));
  }
}