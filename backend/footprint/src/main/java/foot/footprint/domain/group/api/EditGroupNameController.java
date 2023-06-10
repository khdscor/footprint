package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.EditGroupNameService;
import foot.footprint.domain.group.dto.EditGroupNameRequest;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class EditGroupNameController {

  private final EditGroupNameService editGroupNameService;

  @PutMapping("/{groupId}")
  public ResponseEntity<Void> editGroupName(@PathVariable("groupId") Long groupId,
      @RequestBody EditGroupNameRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    editGroupNameService.change(groupId, userDetails.getId(), request.getNewName());

    return ResponseEntity.noContent().build();
  }
}