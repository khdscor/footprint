package foot.footprint.domain.group.dto;

import lombok.Getter;

@Getter
public class CreateGroupRequest {

  private String groupName;

  public CreateGroupRequest(String groupName) {
    this.groupName = groupName;
  }

  public CreateGroupRequest() {
  }
}