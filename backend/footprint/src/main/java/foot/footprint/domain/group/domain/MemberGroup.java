package foot.footprint.domain.group.domain;

import java.util.Date;
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

  public MemberGroup(Long id, Date create_date, Long group_id, Long member_id) {
    this.id = id;
    this.create_date = create_date;
    this.group_id = group_id;
    this.member_id = member_id;
  }
}
