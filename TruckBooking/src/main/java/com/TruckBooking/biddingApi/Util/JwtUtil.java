package com.TruckBooking.biddingApi.Util;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.TruckBooking.biddingApi.Exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String SECRET_KEY = "liveasy";

	//retrieve token Id
	public String extractId(String token) {
		return extractClaim(token, Claims::getId);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken() {
		Claims claims =Jwts.claims()
				.setIssuer("liveasy")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60))
				.setId("blah");


		//		claims.put("phoneNo", transporter.getPhoneNo());

		return Jwts.builder()
				.setClaims(claims)		
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

	public void validateToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(SECRET_KEY)
			.parseClaimsJws(token);
		} catch(Exception e) {
			throw new BusinessException("token invalid");
		}
		if(isTokenExpired(token))
			throw new BusinessException("token expired");
	}
}
