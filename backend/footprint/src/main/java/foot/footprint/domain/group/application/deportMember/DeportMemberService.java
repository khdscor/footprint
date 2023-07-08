package foot.footprint.domain.group.application.deportMember;

public interface DeportMemberService {

    void deport(Long groupId, Long memberId, Long myId);
}