package foot.footprint.domain.comment.dto;

import foot.footprint.domain.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorDto {

    private Long id;
    private String nickName;
    private String imageUrl;

    public AuthorDto(Long id, String nickName, String imageUrl) {
        this.id = id;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
    }

    public static AuthorDto buildAuthorDto(Member member) {
        return new AuthorDto(member.getId(), member.getNick_name(), member.getImage_url());
    }
}