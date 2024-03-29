package foot.footprint.domain.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String image_url;
    private Date join_date;
    private String nick_name;
    private String password;
    private AuthProvider provider;
    private String provider_id;
    private Role role;
    private String refresh_token;

    public Member(Long id, String email, String image_url, Date join_date, String nick_name,
        String password, AuthProvider provider, String provider_id, Role role,
        String refresh_token) {
        this.id = id;
        this.email = email;
        this.image_url = image_url;
        this.join_date = join_date;
        this.nick_name = nick_name;
        this.password = password;
        this.provider = provider;
        this.provider_id = provider_id;
        this.role = role;
        this.refresh_token = refresh_token;
    }

    public static Member createMember(String email, String nickName, String password) {
        return Member.builder()
            .email(email)
            .image_url("https://ifh.cc/g/f5Yjo0.png")
            .provider(AuthProvider.local)
            .nick_name(nickName)
            .role(Role.USER)
            .join_date(new Date())
            .password(password).build();
    }
}