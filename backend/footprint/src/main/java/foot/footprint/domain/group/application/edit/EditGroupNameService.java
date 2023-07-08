package foot.footprint.domain.group.application.edit;

public interface EditGroupNameService {

    void change(Long groupId, Long ownerId, String newName);
}