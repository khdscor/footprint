package foot.footprint.domain.group.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EditGroupNameRequest {

  @NotEmpty(message = "빈 그룹이름을 생성할 수 없습니다.")
  @NotBlank(message = "빈 그룹이름을 생성할 수 없습니다.")
  @Size(max = 20, message = "그룹이름은 20자 내로만 생성할 수 있습니다.")
  private String newName;

  public EditGroupNameRequest(String newName) {
    this.newName = newName;
  }

  public EditGroupNameRequest() {
  }
}