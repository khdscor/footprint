package foot.footprint.repository.user;

import foot.footprint.domain2.user.AuthProvider;
import foot.footprint.domain2.user.Role;
import foot.footprint.domain2.user.User;
import foot.footprint.repository.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends RepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        //given
        Date date = new Date();
        User user = User.builder()
                .id(100L)
                .email("test")
                .image_url(null)
                .provider_id("test")
                .provider(AuthProvider.google)
                .nick_name("tset")
                .role(Role.USER)
                .join_date(date)
                .password("tset").build();
        //when
        assertThat(userRepository.saveUser(user)).isEqualTo(1);

       User foundUser = userRepository.findById(34L).get();

        System.out.println("fffffffffffffffffffffffff");
        System.out.println(foundUser.getProvider());
        System.out.println(foundUser.getRole());
        System.out.println("fffffffffffffffffffffffff");

    }

    @Test
    public void existsByEmail(){
        boolean test1 = userRepository.existsByEmail("testsfd");
        boolean test2 = userRepository.existsByEmail("khdscor@gmail.com");
        System.out.println(";;;;;;;;;;;;;;;;;;;;" +test1);
        System.out.println(";;;;;;;;;;;;;;;;;;;;" +test2);
    }
}
