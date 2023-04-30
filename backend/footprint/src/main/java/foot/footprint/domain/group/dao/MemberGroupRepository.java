package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.MemberGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberGroupRepository {

  Long saveMemberGroup(MemberGroup memberGroup);

  boolean checkAlreadyJoined(Long groupId, Long memberId);

  @Delete("DELETE FROM member_group WHERE group_id=#{groupId} and member_id=#{memberId}")
  int deleteMemberGroup(Long groupId, Long memberId);
}