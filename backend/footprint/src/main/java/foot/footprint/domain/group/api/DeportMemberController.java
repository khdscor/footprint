package foot.footprint.domain.group.api;

import foot.footprint.domain.group.application.DeportMemberService;
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

    @DeleteMapping("/{groupId}/{memberId}")

    public ResponseEntity<Void> deportMember(@PathVariable("groupId") Long groupId,
        @PathVariable("memberId") Long memberId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        deportMemberService.deport(groupId, memberId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }
}