package foot.footprint.domain.member.api;

import foot.footprint.domain.member.application.member.MemberService;
import foot.footprint.domain.member.dto.MemberImageResponse;
import foot.footprint.domain.member.dto.MyPageResponse;
import foot.footprint.global.aop.auth.MemberLog;
import foot.footprint.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/me")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/image")
    public ResponseEntity<MemberImageResponse> findMemberImageUrl(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberImageResponse response = memberService.findImageUrl(userDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<MyPageResponse> findMine(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        MyPageResponse response = memberService.findMyPageInfo(userDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @MemberLog
    @DeleteMapping
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.withdraw(userDetails.getId());

        return ResponseEntity.noContent()
            .build();
    }
}