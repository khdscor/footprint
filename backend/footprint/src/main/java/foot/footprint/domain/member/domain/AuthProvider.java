package foot.footprint.domain.member.domain;

import foot.footprint.global.typeHandler.CodeEnum;
import foot.footprint.global.typeHandler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

public enum AuthProvider implements CodeEnum {
  local("LOCAL"),
  google("GOOGLE"),
  naver("NAVER"),
  kakao("KAKAO");

  private String code;

  AuthProvider(String code) {
    this.code = code;
  }

  @MappedTypes(AuthProvider.class)
  public static class TypeHandler extends CodeEnumTypeHandler<AuthProvider> {

    public TypeHandler() {
      super(AuthProvider.class);
    }
  }

  @Override
  public String getCode() {
    return code;
  }
}