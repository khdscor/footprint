package foot.footprint.domain.group.domain;

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
public class MemberGroup {

  private Long id;
  private Date create_date;
  private Long group_id;
  private Long member_id;
  private boolean important;

  public MemberGroup(Long id, Date create_date, Long group_id, Long member_id, boolean important) {
    this.id = id;
    this.create_date = create_date;
    this.group_id = group_id;
    this.member_id = member_id;
    this.important = important;
  }

  public static MemberGroup createMemberGroup(Group group){
    return MemberGroup.builder()
        .create_date(new Date())
        .group_id(group.getId())
        .member_id(group.getOwner_id())
        .important(false).build();
  }

  public void changeImportant() {
    if (Objects.isNull(important)) {
      important = true;
    } else {
      important = !important;
    }
  }
}