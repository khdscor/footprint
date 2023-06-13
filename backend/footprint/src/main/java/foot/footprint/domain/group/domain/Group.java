package foot.footprint.domain.group.domain;

import foot.footprint.global.error.exception.WrongInputException;
import java.util.Date;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class Group {

    private Long id;
    private Date create_date;
    private String invitation_code;
    private String name;
    private Long owner_id;

    public Group(Long id, Date create_date, String invitation_code, String name, Long owner_id) {
        this.id = id;
        this.create_date = create_date;
        addInvitationCode(invitation_code);
        validate(name);
        this.name = name;
        this.owner_id = owner_id;
    }

    public Group(String name, Long owner_id) {
        this.create_date = new Date();
        validate(name);
        this.name = name;
        this.owner_id = owner_id;
    }

    private void validate(String name) {
        if (Objects.isNull(name)) {
            throw new WrongInputException("그룹이름이 null 일 수 없습니다.");
        }
    }

    public void addInvitationCode(String invitation_code) {
        if (Objects.nonNull(this.invitation_code) && !this.invitation_code.isEmpty()) {
            throw new WrongInputException("초대코드는 처음 한번만 정할 수 있습니다. 이미 부여된 초대코드가 존재합니다.");
        }
        this.invitation_code = invitation_code;
    }
}