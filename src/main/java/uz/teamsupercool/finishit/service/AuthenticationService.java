/*
 * FINISH.IT Task Manager
 * Final project of Application Programming in Java Course | Fall 2021
 *
 * Developed by TeamSuperCool:
 *
 * Aslkhon Khoshimkhujaev U2010145
 * Dilmurod Sagatov U2010235
 * Saidamalkhon Inoyatov U2010093
 * David Suleymanov U2010271
 * */

package uz.teamsupercool.finishit.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
* Authentication Service is used by Spring Boot Security filter after each
* request to check whether user with given username and password exists in DB.
* */

@Service
public class AuthenticationService implements UserDetailsService {
	final UserService service;

	public AuthenticationService(UserService service) {
		this.service = service;
	}

	/*
	* Returns Framework UserDetails Class object if user with such username exists
	* else throws UserNotFoundException
	* */
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