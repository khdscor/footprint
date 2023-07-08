package foot.footprint.domain.group.application.edit;

import foot.footprint.domain.group.dao.GroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditGroupNameServiceImpl implements EditGroupNameService {

    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public void change(Long groupId, Long ownerId, String newName) {
        int edited = groupRepository.changeGroupName(groupId, ownerId, newName);
        if (edited == 0) {
            throw new NotAuthorizedOrExistException("그룹 이름 변경할 권한이 없습니다.");
        }
    }
}