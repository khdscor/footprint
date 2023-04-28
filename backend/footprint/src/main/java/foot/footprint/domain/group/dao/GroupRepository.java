package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.Group;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupRepository {

  Long saveGroup(Group group);

  @Select("Select * from group_table where id=#{groupId}")
  Optional<Group> findById(Long groupId);
}
