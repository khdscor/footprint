package foot.footprint.domain.member.dto;

import lombok.Getter;

@Getter
public class MyIdResponse {
  private Long myId;

  public MyIdResponse(Long myId) {
    this.myId = myId;
  }
}