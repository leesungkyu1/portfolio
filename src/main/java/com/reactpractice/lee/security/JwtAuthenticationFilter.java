package com.reactpractice.lee.security;

import com.reactpractice.lee.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * JwtTokenProvider설정 클래스를 주입한다.
     *
     * @param jwtTokenProvider - jwtTokenProvider관련 모듈을 작성한 JwtTokenProvider
     * @exception Exception
     */
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Request로 들어오는 Jwt Token의 유효성을 검사(jwtTokenProvider.validateToken)하는 filter를
     * filterChain에 등록한다.
     *
     * @param request - 요청에 대한 정보를 담고 있는 ServletRequest response - 응답에 대한 정보를 담고 있는
     *                response chain - 필터에 대한 정보를 담고 있는 FilterChain
     * @exception Exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);
        String resultCode = null;

        if (token != null) {
            resultCode = jwtTokenProvider.validateToken(token);
        }

        if (token != null && resultCode.equals("01")) {
            String userKey = jwtTokenProvider.getUserKey(token);
            Collection<? extends GrantedAuthority> roles = jwtTokenProvider.getRoles(token);

            CustomUserDetails userDetails = new CustomUserDetails();

            userDetails.setUserKey(Integer.parseInt(userKey));
            userDetails.setUserAuth(roles);

            token = jwtTokenProvider.newToken(userDetails);

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .sameSite("lax")
                    .path("/")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());

            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } else if (token != null && resultCode.equals("02")) {
            Cookie[] cookieArr = request.getCookies();

            if(cookieArr != null) {
                for(int i=0; i<cookieArr.length; i++) {
                    String name = cookieArr[i].getName();
                    if(name.equals("token")) {
                        cookieArr[i].setMaxAge(0);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
