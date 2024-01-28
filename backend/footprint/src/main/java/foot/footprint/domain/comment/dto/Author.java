package foot.footprint.domain.comment.dto;

import foot.footprint.domain.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Author {

    private Long id;
    private String nickName;
    private String imageUrl;

    public Author(Long id, String nickName, String imageUrl) {
        this.id = id;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
    }

    public static Author buildAuthor(Member member) {
        return new Author(member.getId(), member.getNick_name(), member.getImage_url());
    }
}