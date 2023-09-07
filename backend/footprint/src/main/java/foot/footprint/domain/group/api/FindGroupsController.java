package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.findGroup.FindGroupsService;
import foot.footprint.domain.group.dto.find.GroupSummaryResponse;
import foot.footprint.global.security.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("groups")
public class FindGroupsController {

    private final FindGroupsService findMyImportantGroupsService;

    private final FindGroupsService findMyGroupsService;

    public FindGroupsController(
            @Qualifier("importantGroups") FindGroupsService findMyImportantGroupsService,
            @Qualifier("groups") FindGroupsService findMyGroupsService) {
        this.findMyImportantGroupsService = findMyImportantGroupsService;
        this.findMyGroupsService = findMyGroupsService;
    }

    @GetMapping("/mine/important")
    public ResponseEntity<List<GroupSummaryResponse>> findMyImportantGroups(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GroupSummaryResponse> responses = findMyImportantGroupsService.findGroups(userDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<GroupSummaryResponse>> findMyGroups(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GroupSummaryResponse> responses = findMyGroupsService.findGroups(userDetails.getId());

        return ResponseEntity.ok().body(responses);
    }
}