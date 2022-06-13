package com.reactpractice.lee.jwt;

import com.reactpractice.lee.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String JWT_SECRET = "secretKey";

    //토큰 유효시간
    private static final int JWT_EXPIRATION_MS = 604800000;

    //jwt 토큰 생성
    public String createToken(UserDetails userDetails, UserVO userVO){
        Claims claims = Jwts.claims().setSubject(Integer.toString(userVO.getUserKey()));
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

}
