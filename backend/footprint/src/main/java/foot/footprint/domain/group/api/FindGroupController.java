package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.FindGroupService;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import foot.footprint.global.security.user.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("groups")
@RequiredArgsConstructor
public class FindGroupController {

  private final FindGroupService findGroupService;

  @GetMapping("/important")
  public ResponseEntity<List<GroupSummaryResponse>> findMyImportantGroups(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    List<GroupSummaryResponse> responses = findGroupService.findMyImportantGroups(
        userDetails.getId());

    return ResponseEntity.ok().body(responses);
  }
}