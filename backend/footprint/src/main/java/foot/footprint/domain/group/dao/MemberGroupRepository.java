package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.MemberGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberGroupRepository {

  Long saveMemberGroup(MemberGroup memberGroup);
}
