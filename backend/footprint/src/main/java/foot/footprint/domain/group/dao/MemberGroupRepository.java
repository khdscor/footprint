package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.dto.GroupSummaryResponse;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberGroupRepository {

  Long saveMemberGroup(MemberGroup memberGroup);

  boolean checkAlreadyJoined(Long groupId, Long memberId);

  @Delete("DELETE FROM member_group WHERE group_id=#{groupId} and member_id=#{memberId}")
  int deleteMemberGroup(Long groupId, Long memberId);

  @Select("SELECT COUNT(*) FROM member_group WHERE group_id=#{groupId}")
  Long countMemberGroup(Long groupId);

  int changeImportant(Long groupId, Long memberId);

  @Select("SELECT * FROM member_group WHERE id=#{id}")
  MemberGroup findById(Long id);

  List<GroupSummaryResponse> findMyImportantGroups(Long memberId);
}