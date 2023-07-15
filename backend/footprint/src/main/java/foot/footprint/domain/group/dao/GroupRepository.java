package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.Group;
import foot.footprint.domain.group.dto.find.GroupDetailsDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GroupRepository {

    Long saveGroup(Group group);

    @Select("Select * from group_table where id=#{groupId}")
    Optional<Group> findById(Long groupId);

    @Select("Select * from group_table where invitation_code=#{invitationCode}")
    Optional<Group> findByInvitationCode(String invitationCode);

    @Update("UPDATE group_table SET invitation_code=#{invitation_code} WHERE id=#{id}")
    int updateInvitationCode(Group group);

    int changeGroupName(Long groupId, Long ownerId, String newName);

    @Delete("DELETE FROM group_table WHERE id=#{groupId}")
    int deleteById(Long groupId);

    List<Long> findAllByMemberId(Long memberId);

    Optional<GroupDetailsDto> findGroupDetails(Long groupId, Long memberId);

    Optional<String> findGroupName(Long memberId, Long groupId);

    @Update("UPDATE group_table SET owner_id = #{memberId} WHERE id = #{groupId}")
    int updateOwner(Long groupId, Long memberId);
}