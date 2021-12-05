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

package uz.teamsupercool.finishit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import uz.teamsupercool.finishit.config.JwtTokenUtil;
import uz.teamsupercool.finishit.model.AuthenticationResponse;
import uz.teamsupercool.finishit.model.User;
import uz.teamsupercool.finishit.service.UserService;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@CrossOrigin
public class AuthenticationController {
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtTokenUtil util;

	@Autowired
	private UserDetailsService service;

	private final UserService userService;

	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}

	/*
	* Authenticate path requires username and password.
	*
	* If user exists in DB and password is correct generates a token for 24 hours.
	* */
	@PostMapping
	@RequestMapping("api/auth")
	public ResponseEntity<Object> createAuthenticationToken(@RequestBody User request)
			throws Exception {

		authenticate(request.getUsername(), request.getPassword());

		final UserDetails details = service
				.loadUserByUsername(request.getUsername());

		final String token = util.generateToken(details);

		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	/*
	* Register path requires username and password to create new user.
	*
	* Returns 201 CREATED status.
	* If user already exists return 403 FORBIDDEN status.
	* */
	@PostMapping
	@RequestMapping("api/register")
	public ResponseEntity<Object> register(@RequestBody User request) throws Exception {
		final String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

		try {
			userService.addUser(new User(null, request.getUsername(), encryptedPassword, new ArrayList<>()));
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		authenticate(request.getUsername(), request.getPassword());

		final UserDetails details = service
				.loadUserByUsername(request.getUsername());

		final String token = util.generateToken(details);

		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
}
