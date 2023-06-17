package foot.footprint.domain.group.application;

import foot.footprint.domain.group.dao.MemberGroupRepository;
import foot.footprint.global.error.exception.NotAuthorizedOrExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeImportantService {

    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public void changeImportant(Long groupId, Long memberId) {
        int result = memberGroupRepository.changeImportant(groupId, memberId);
        if (result == 0) {
            throw new NotAuthorizedOrExistException("해당 그룹이 존재하지 않거나 즐겨찾기를 변경할 권한이 없습니다.");
        }
    }
}