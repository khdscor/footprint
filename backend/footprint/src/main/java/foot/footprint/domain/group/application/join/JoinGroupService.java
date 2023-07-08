package foot.footprint.domain.group.application.join;

public interface JoinGroupService {

    Long join(String invitationCode, Long memberId);
}