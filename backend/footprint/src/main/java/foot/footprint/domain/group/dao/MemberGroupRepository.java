package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.domain.MemberGroup;
import foot.footprint.domain.group.dto.find.GroupSummaryResponse;
import java.util.List;
import java.util.Optional;
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
    Optional<MemberGroup> findById(Long id);

    List<Group> findMyImportantGroups(Long memberId);

    List<Group> findMyGroups(Long memberId);

    @Select("SELECT * FROM member_group WHERE group_id = #{groupId} ORDER BY id")
    List<MemberGroup> findAllByGroupId(Long groupId);

    boolean existsMemberInGroup(Long memberId, Long groupId);
}