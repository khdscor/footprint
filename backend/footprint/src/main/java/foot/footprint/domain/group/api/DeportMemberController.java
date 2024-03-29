package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.deportMember.DeportMemberService;
import foot.footprint.domain.group.dto.DeportMemberCommand;
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
public class DeportMemberController {

    private final DeportMemberService deportMemberService;

    @GroupLog
    @DeleteMapping("/{groupId}/{memberId}")

    public ResponseEntity<Void> deportMember(@PathVariable("groupId") Long groupId,
        @PathVariable("memberId") Long memberId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        deportMemberService.deport(new DeportMemberCommand(groupId, memberId, userDetails.getId()));

        return ResponseEntity.noContent().build();
    }
}