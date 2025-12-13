package com.demo.SweetShop.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.SweetShop.model.MyUser;
import com.demo.SweetShop.service.JWTService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			Claims claims = jwtService.verifyClaims(authHeader.substring(7));
			if (claims != null) {

				String role = claims.get("role", String.class);
				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						new MyUser((int) claims.get("id"), claims.getSubject(), null, null), null, authorities);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}