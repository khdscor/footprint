package foot.footprint.domain.user.dao;

import foot.footprint.domain.user.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserRepository {
    @Insert("INSERT INTO user(email, image_url, join_date, nick_name, password, provider, provider_id, role) " +
            "VALUES (#{email}, #{image_url}, #{join_date}, #{nick_name}, #{password}, #{provider}, #{provider_id}, #{role})")
    int saveUser(User user);

    @Select("SELECT * FROM user WHERE id=#{id}")
    Optional<User> findById(Long id);

    @Select("SELECT * FROM user WHERE email=#{email}")
    Optional<User> findByEmail(String email);

    @Update("UPDATE user SET refresh_token=#{token} WHERE id=#{id}")
    void updateRefreshToken(Long id, String token);

    @Select("SELECT EXISTS (SELECT 1 FROM user WHERE email = #{email})")
    boolean existsByEmail(String email);
}
