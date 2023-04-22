package foot.footprint.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import foot.footprint.domain.member.application.MemberService;
import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.domain.member.dto.MemberImageResponse;
import foot.footprint.global.error.exception.NotExistsException;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @Spy
  private MemberRepository memberRepository;

  @Spy
  @InjectMocks
  private MemberService memberService;

  private Member member;

  @Test
  public void findImageUrl() {
    //given
    setMember();
    given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

    //when
    MemberImageResponse response = memberService.findImageUrl(member.getId());

    //then
    verify(memberRepository, times(1)).findById(any());
    verify(memberService, times(1)).findImageUrl(any());
    assertThat(response.getImageUrl()).isNull();
    assertThat(response.getMemberId()).isEqualTo(member.getId());
  }

  @Test
  @DisplayName("해당하는 회원이 존재하지 않을 때")
  public void findImageUr_IfNotExistsMember() {
    //given
    Long memberId = 1L;
    //when & then
    assertThatThrownBy(
        () -> memberService.findImageUrl(memberId))
        .isInstanceOf(NotExistsException.class);
  }

  private void setMember() {
    member = Member.builder()
        .id(20L)
        .email("test")
        .image_url(null)
        .provider_id("test")
        .provider(AuthProvider.google)
        .nick_name("tset")
        .role(Role.USER)
        .join_date(new Date())
        .password("password").build();
  }
}