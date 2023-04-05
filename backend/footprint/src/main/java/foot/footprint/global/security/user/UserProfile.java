package foot.footprint.global.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfile {

  private final String providerId;
  private final String name;
  private final String email;
  private final String imageUrl;
}