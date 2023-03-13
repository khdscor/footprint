package foot.footprint.repository.user;

import foot.footprint.domain.user.AuthProvider;
import foot.footprint.domain.user.Role;
import foot.footprint.domain.user.User;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class SaveUserRepositoryTest extends RepositoryTest {
    @Autowired
    private SaveUserRepository saveUserRepository;

    @Test
    public void saveUser() {
        //given
        Date date = new Date();
        User user = User.builder()
                .email("test")
                .image_url(null)
                .provider_id("test")
                .provider(AuthProvider.google)
                .nick_name("tset")
                .role(Role.USER)
                .join_date(date)
                .password("tset").build();
        //when
        saveUserRepository.saveUser(user);
    }
}
