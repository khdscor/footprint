package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.findGroup.FindGroupService;
import foot.footprint.domain.group.dto.find.GroupDetailsResponse;
import foot.footprint.domain.group.dto.find.GroupNameResponse;
import foot.footprint.domain.group.dto.find.GroupSummaryResponse;
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

    @GetMapping("/{id}")
    public ResponseEntity<GroupDetailsResponse> findOne(@PathVariable("id") Long groupId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        GroupDetailsResponse response = findGroupService.findOne(groupId, userDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<GroupNameResponse> findGroupName(@PathVariable("id") Long groupId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        GroupNameResponse response = findGroupService.findName(groupId, userDetails.getId());

        return ResponseEntity.ok().body(response);
    }
}