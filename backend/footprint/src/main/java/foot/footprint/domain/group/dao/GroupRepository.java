package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.Group;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupRepository {

  Long saveGroup(Group group);
}
