package org.roger.crm.controller ;
import java.util.Objects;

import org.roger.crm.service.TokenService;
import org.roger.crm.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenUtility utility;

	@Autowired
	private TokenService service;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request) throws Exception {
		authenticate(request.getUsername(), request.getPassword());

		final UserDetails userDetails = service.loadUserByUsername(request.getUsername());

		final String token = utility.generateToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}