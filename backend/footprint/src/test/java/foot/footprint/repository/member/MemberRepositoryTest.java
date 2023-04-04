package foot.footprint.repository.member;

import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest extends RepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  private String Email = "email";

  @Test
  public void saveMember() {
    //given
    Member member = buildMember();

    //when & then
    assertThat(memberRepository.saveMember(member)).isNotNull();
  }

  @Test
  public void existsByEmail() {
    //given
    saveOne();

    //when & then
    assertThat(memberRepository.existsByEmail("test")).isFalse();
    assertThat(memberRepository.existsByEmail(Email)).isTrue();
  }

  @Test
  public void findByEmail() {
    //given
    saveOne();

    //when & then
    assertThat(memberRepository.findByEmail(Email).get().getNick_name()).isEqualTo("nickName");
  }

  private void saveOne() {
    Member member = buildMember();
    memberRepository.saveMember(member);
  }

  private Member buildMember() {
    return Member.builder()
        .nick_name("nickName")
        .email(Email)
        .provider(AuthProvider.google)
        .password("password")
        .provider_id("test")
        .image_url(null)
        .join_date(new Date())
        .role(Role.USER)
        .build();
  }
}