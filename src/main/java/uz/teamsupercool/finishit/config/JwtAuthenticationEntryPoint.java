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

package uz.teamsupercool.finishit.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*
* Called when user tries access paths without authorization. Sends Unauthorized error as response.
* */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
