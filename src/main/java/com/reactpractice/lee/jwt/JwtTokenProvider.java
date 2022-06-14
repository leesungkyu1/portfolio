package com.reactpractice.lee.jwt;

import com.reactpractice.lee.security.AccountService;
import com.reactpractice.lee.security.CustomUserDetails;
import com.reactpractice.lee.vo.UserVO;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Autowired
    private AccountService accountService;

    private static final String JWT_SECRET = "secretKey";

    //토큰 유효시간
    private static final int JWT_EXPIRATION_MS = 604800000;

    //jwt 토큰 생성
    public String createToken(CustomUserDetails userDetails){
        Claims claims = Jwts.claims().setSubject(Integer.toString(userDetails.getUserKey()));
        claims.put("role", userDetails.getAuthorities());
        claims.put("userId", userDetails.getUsername());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();

    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = accountService.loadUserByUserKey(this.getUserKey(token));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String getUserKey(String token){
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public Collection<? extends GrantedAuthority> getRoles(String token){
        return (Collection<? extends GrantedAuthority>)Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().get("roles");
    }

    public String resolveToken(HttpServletRequest request) {
        /* return request.getHeader("X-AUTH-TOKEN"); */

        final Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);
                return cookie.getValue();
            }
        }
        return null;
    }

    public String validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
            if(!claims.getBody().getExpiration().before(new Date())) {
                return "01";
            }else {
                return "03";
            }
        }catch (ExpiredJwtException e) {
            return "02";
        }
        catch(Exception e) {
            e.printStackTrace();
            return "03";
        }
    }

    public String newToken(CustomUserDetails userDetails) {
        return createToken(userDetails);
    }
}
