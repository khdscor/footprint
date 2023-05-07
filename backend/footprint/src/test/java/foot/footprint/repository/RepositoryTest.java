package foot.footprint.repository;

import foot.footprint.domain.article.domain.Article;
import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.member.domain.AuthProvider;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.domain.Role;
import java.util.Date;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
@ActiveProfiles("test")
@Sql(value = "/init-table.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class RepositoryTest {

  protected Member buildMember() {
    return Member.builder()
        .nick_name("nickName")
        .email("email")
        .provider(AuthProvider.google)
        .password("password")
        .provider_id("test")
        .image_url(null)
        .join_date(new Date())
        .role(Role.USER)
        .build();
  }

  protected Article buildArticle(Long memberId) {
    return Article.builder()
        .content("test")
        .latitude(10.0)
        .longitude(10.0)
        .public_map(true)
        .private_map(true)
        .title("test")
        .create_date(new Date())
        .member_id(memberId).build();
  }

  protected Group buildGroup(Long ownerId) {
    return Group.builder()
        .create_date(new Date())
        .name("test_group")
        .invitation_code("testCode")
        .owner_id(ownerId).build();
  }
}