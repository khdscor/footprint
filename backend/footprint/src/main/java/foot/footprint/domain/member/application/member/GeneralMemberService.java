package foot.footprint.domain.member.application.member;

import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.dto.MemberImageResponse;
import foot.footprint.domain.member.dto.MyPageResponse;
import foot.footprint.global.error.exception.NotExistsException;
import foot.footprint.global.util.ObjectSerializer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService {

    private final MemberRepository memberRepository;

    private final ObjectSerializer objectSerializer;

    @Override
    @Transactional(readOnly = true)
    public MemberImageResponse findImageUrl(Long memberId) {
        String redisKey = "memberImageUrl::" + memberId;
        Optional<MemberImageResponse> cacheData = findByRedis(redisKey);
        if (cacheData.isPresent()) {
            return cacheData.get();
        }
        MemberImageResponse response = findByDB(memberId);
        objectSerializer.saveData(redisKey, response, 5);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public MyPageResponse findMyPageInfo(Long id) {
        return memberRepository.findMyPageDetails(id);
    }

    @Override
    @Transactional
    public void withdraw(Long id) {
        int deleted = memberRepository.deleteMember(id);
        if (deleted == 0) {
            throw new NotExistsException("존재하지 않는 계정입니다.");
        }
    }

    private Optional<MemberImageResponse> findByRedis(String redisKey) {
        return objectSerializer.getData(redisKey, MemberImageResponse.class);
    }

    private MemberImageResponse findByDB(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotExistsException("존재하지 않는 회원입니다."));
        return new MemberImageResponse(member.getId(), member.getImage_url());
    }
}