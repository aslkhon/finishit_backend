package uz.teamsupercool.finishit.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
	final UserService service;

	public AuthenticationService(UserService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			final uz.teamsupercool.finishit.model.User user = service.getUserByUsername(username);
			return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		} catch (Exception exception) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}