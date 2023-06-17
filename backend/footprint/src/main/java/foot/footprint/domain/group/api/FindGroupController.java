package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.FindGroupService;
import foot.footprint.domain.group.dto.GroupDetailsResponse;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import foot.footprint.global.security.user.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("groups")
@RequiredArgsConstructor
public class FindGroupController {

    private final FindGroupService findGroupService;

    @GetMapping("/mine/important")
    public ResponseEntity<List<GroupSummaryResponse>> findMyImportantGroups(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GroupSummaryResponse> responses = findGroupService.findMyImportantGroups(
            userDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<GroupSummaryResponse>> findMyGroups(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GroupSummaryResponse> responses = findGroupService.findMyGroups(userDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDetailsResponse> findOne(@PathVariable("id") Long groupId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        GroupDetailsResponse response = findGroupService.findOne(groupId, userDetails.getId());

        return ResponseEntity.ok().body(response);
    }
}