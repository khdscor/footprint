package foot.footprint.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Getter
@ToString
@Builder
@NoArgsConstructor
public class User {
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

    public User(Long id, String email, String image_url, Date join_date, String nick_name, String password,
                AuthProvider provider, String provider_id, Role role, String refresh_token) {
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
}
