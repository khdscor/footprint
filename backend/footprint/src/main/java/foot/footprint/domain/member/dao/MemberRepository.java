package foot.footprint.domain.member.dao;

import foot.footprint.domain.member.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Mapper
@Repository
public interface MemberRepository {


  Long saveMember(Member member);

  @Select("SELECT * FROM member WHERE id=#{id}")
  Optional<Member> findById(Long id);

  @Select("SELECT * FROM member WHERE email=#{email}")
  Optional<Member> findByEmail(String email);

  @Update("UPDATE member SET refresh_token=#{token} WHERE id=#{id}")
  void updateRefreshToken(Long id, String token);

  @Select("SELECT EXISTS (SELECT 1 FROM member WHERE email = #{email})")
  boolean existsByEmail(String email);
}