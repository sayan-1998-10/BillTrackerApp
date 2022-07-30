package com.project.tracker.security.jwtFilters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtValidationFilter extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String bearerToken = request.getHeader("Authorization");
		
		if(bearerToken==null || !StringUtils.hasText(bearerToken) && !bearerToken.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwt = bearerToken.split(" ")[1];
		String key = SECRET_KEY;
		
		Jws<Claims> parsedJws = Jwts
			.parser()
			.setSigningKey(new SecretKeySpec(key.getBytes(),"HS256"))
			.parseClaimsJws(jwt);
		
		String subject = parsedJws.getBody().getSubject();
		List<Map<String,String>> authorities = (List<Map<String,String>>)parsedJws.getBody().get("authorities");
		
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		authorities.forEach(authorityMap ->{
			grantedAuthorities.add(new SimpleGrantedAuthority(authorityMap.get("authority")));
		});
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(subject, null,grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);
	}

}
