package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.CreateGroupService;
import foot.footprint.domain.group.dto.CreateGroupRequest;
import foot.footprint.global.error.exception.ContentEmptyException;
import foot.footprint.global.error.exception.LengthOverException;
import foot.footprint.global.security.user.CustomUserDetails;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class CreateGroupController {

  private final CreateGroupService createGroupService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody CreateGroupRequest createGroupRequest,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String groupName = createGroupRequest.getGroupName();
    validateName(groupName);
    Long groupId = createGroupService.createGroup(groupName, userDetails.getId());

    return ResponseEntity.created(URI.create("/groups/" + groupId))
        .build();
  }

  private void validateName(String groupName) {
    if (groupName.length() >= 20) {
      throw new LengthOverException("이름이 지정된 최대길이를 초과하였습니다.");
    }
    if (groupName.isEmpty() || groupName.isBlank()) {
      throw new ContentEmptyException("이름을 입력해 주십시오.");
    }
  }
}