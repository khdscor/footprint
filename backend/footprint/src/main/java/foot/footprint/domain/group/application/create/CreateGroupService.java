package foot.footprint.domain.group.application.create;

public interface CreateGroupService {

    Long createGroup(String groupName, Long creatorId);
}