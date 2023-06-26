package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.SecessionGroupService;
import foot.footprint.global.aop.group.GroupLog;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class SecessionGroupController {

    private final SecessionGroupService secessionGroupService;

    @GroupLog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sessionGroup(@PathVariable("id") Long groupId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        secessionGroupService.secessionGroup(groupId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }
}