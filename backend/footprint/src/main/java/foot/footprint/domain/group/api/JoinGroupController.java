package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.JoinGroupService;
import foot.footprint.global.security.user.CustomUserDetails;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class JoinGroupController {

    private final JoinGroupService joinGroupService;

    @PostMapping("/admission")
    public ResponseEntity<Void> joinGroup(@RequestParam(value = "invitation_code") String invitationCode,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long groupId = joinGroupService.join(invitationCode, userDetails.getId());

        return ResponseEntity.created(URI.create("/groups/" + groupId))
            .build();
    }
}