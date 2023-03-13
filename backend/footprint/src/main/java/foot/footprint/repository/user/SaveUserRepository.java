package foot.footprint.repository.user;

import foot.footprint.domain.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface SaveUserRepository {
    @Insert("INSERT INTO user(email, image_url, join_date, nick_name, password, provider, provider_id, role) " +
            "VALUES (#{email}, #{image_url}, #{join_date}, #{nick_name}, #{password}, #{provider}, #{provider_id}, #{role})")
    void saveUser(User user);
}
