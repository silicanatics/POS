package com.nkd.pos.posapi.controller;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nkd.pos.posapi.config.JwtUtil;
import com.nkd.pos.posapi.entity.AuthenticationRequest;
import com.nkd.pos.posapi.entity.AuthenticationResponse;
import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.License;
import com.nkd.pos.posapi.entity.Usr;
import com.nkd.pos.posapi.service.LicenseService;
import com.nkd.pos.posapi.service.MyUserDetailsService;
import com.nkd.pos.posapi.service.UserService;

import antlr.collections.Enumerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private LicenseService licenseService;


	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			licenseService.updateLicenseInformation();
					
			String username = null;
			logger.info(" createAuthenticationToken...." + encoder.encode(authenticationRequest.getPassword()));

			try {
				// authenticationRequest.setPassword("{bcrypt}"
				// +encoder.encode(authenticationRequest.getPassword()));
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			} catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password", e);
			}

			username = authenticationRequest.getUsername();

			final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			Usr user = userService.getByUsername(username).get();
			EntityAddress entityAddress = userService.getUserAdress(user);
			user.setEntityAddress(entityAddress);
			user.setToken(jwt);

			return ResponseEntity.ok(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims

		String token = request.getHeader("Authorization");
		logger.info(" refreshtoken...." + token);
		token = token.substring(7);
		Claims claims;
		claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String newToken = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		logger.info(" newToken...." + newToken);
		return ResponseEntity.ok(new AuthenticationResponse(newToken));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(Claims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}



}
