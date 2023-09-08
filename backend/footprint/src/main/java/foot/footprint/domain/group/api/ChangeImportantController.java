package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.changeImportant.ChangeGroupImportantService;
import foot.footprint.global.aop.group.GroupLog;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class ChangeImportantController {

    private final ChangeGroupImportantService changeGroupImportantService;

    @GroupLog
    @PutMapping("/{groupId}/important")
    public ResponseEntity<Void> changeImportant(@PathVariable("groupId") Long groupId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        changeGroupImportantService.changeImportant(groupId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }
}