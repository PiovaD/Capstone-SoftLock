package softLock.security.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {

	private String token;
	private Long id;
	private String username;
	private String email;
	private String profilePicUrl;

	private List<String> roles;

	private Date expirationTime;


}
