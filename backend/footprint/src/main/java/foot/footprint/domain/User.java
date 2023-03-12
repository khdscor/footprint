package foot.footprint.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@Builder
@NoArgsConstructor
public class User {

    private Long id;
    private String email;
    private String image_url;
    private Timestamp join_date;
    private String nick_name;
    private String password;
    private String provider;
    private String provider_id;
    private String role;

}
