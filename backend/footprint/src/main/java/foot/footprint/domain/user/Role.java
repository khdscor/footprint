package foot.footprint.domain.user;


import foot.footprint.domain.typeHandler.CodeEnum;
import foot.footprint.domain.typeHandler.CodeEnumTypeHandler;
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
