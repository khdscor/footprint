package foot.footprint.domain.member.application;

import foot.footprint.domain.member.dao.MemberRepository;
import foot.footprint.domain.member.domain.Member;
import foot.footprint.domain.member.dto.MemberImageResponse;
import foot.footprint.domain.member.dto.MyPageResponse;
import foot.footprint.global.error.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberImageResponse findImageUrl(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotExistsException("존재하지 않는 회원입니다."));
        return new MemberImageResponse(member.getId(), member.getImage_url());
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
        if(deleted ==0){
            throw new NotExistsException("존재하지 않는 계정입니다.");
        }
    }
}