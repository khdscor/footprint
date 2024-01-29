package foot.footprint.domain.member.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCommand {

    private String email;
    private String password;
}