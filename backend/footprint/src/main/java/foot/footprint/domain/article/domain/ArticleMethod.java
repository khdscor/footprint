package foot.footprint.domain.article.domain;

public enum ArticleMethod {

  Edit("수정"), Delete("삭제");

  private String code;

  ArticleMethod(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}