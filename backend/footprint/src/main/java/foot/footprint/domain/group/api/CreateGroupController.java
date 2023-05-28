package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.CreateGroupService;
import foot.footprint.domain.group.dto.CreateGroupRequest;
import foot.footprint.global.security.user.CustomUserDetails;
import java.net.URI;
import javax.validation.Valid;
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
  public ResponseEntity<Void> create(@RequestBody @Valid CreateGroupRequest createGroupRequest,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String groupName = createGroupRequest.getGroupName();
    Long groupId = createGroupService.createGroup(groupName, userDetails.getId());

    return ResponseEntity.created(URI.create("/groups/" + groupId))
        .build();
  }
}