package net.cartola.emissorfiscal.security.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 *	24 de dez de 2019
 *	@author robson.costa
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	public static final String SIGNING_KEY = "Um@Chac3P%raD3ix#rCo@!i2ass3Cr%wtas";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		if (!path.contains("/obter-token") && !path.contains("/usuario")) {
			String header = request.getHeader(HEADER_STRING);
			String username = null;
			String authToken = null;
			if (header != null && header.startsWith(TOKEN_PREFIX)) {
				authToken = header.replace(TOKEN_PREFIX, "");
				try {
					username = jwtTokenUtil.getUsernameFromToken(authToken);
				} catch (IllegalArgumentException e) {
					logger.error("an error occured during getting username from token", e);
				} catch (ExpiredJwtException e) {
					logger.warn("the token is expired and not valid anymore", e);
				} catch (SignatureException e) {
					logger.error("Authentication Failed. Username or Password not valid.");
				}
			} else {
				logger.warn("couldn't find bearer string, will ignore the header");
			}
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtTokenUtil.validateToken(authToken, userDetails)) {
					settingAuthentication(request, userDetails, userDetails.getAuthorities());
					logger.info("authenticated user " + username + ", setting security context");
				} else {
					settingAuthentication(request, userDetails, null);
					logger.info("unauthenticated user " + username + ", setting security context");
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	private void settingAuthentication(HttpServletRequest req, UserDetails userDetails,
			Collection<? extends GrantedAuthority> authorities) {
		UsernamePasswordAuthenticationToken authentication = null;
		if (authorities.isEmpty()) {
			authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		} else {
			authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		}
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
}
