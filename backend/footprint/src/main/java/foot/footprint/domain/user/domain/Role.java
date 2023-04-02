package foot.footprint.domain.user.domain;

import foot.footprint.global.typeHandler.CodeEnum;
import foot.footprint.global.typeHandler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

public enum Role implements CodeEnum {
  USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

  private String code;

  Role(String code) {
    this.code = code;
  }

  @MappedTypes(Role.class)
  public static class TypeHandler extends CodeEnumTypeHandler<Role> {

    public TypeHandler() {
      super(Role.class);
    }
  }

  @Override
  public String getCode() {
    return code;
  }
}