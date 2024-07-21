package com.example.autoHRM_backend.auth.jwt;

import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    //JWTUtil 주입
    private final JWTUtil jwtUtil;
    // 이게 회원 검증
    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String email = obtainEmail(request);
        String password = obtainPassword(request);

        System.out.println("LoginFilter.attemptAuthentication");
        System.out.println(email);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함  ,, 여기의 null 에 role 이 들어감
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달 , db 에서 비교해서 검증함. 이때, UserDetailsService가 필요
        return authenticationManager.authenticate(authToken);
    }

    protected String obtainEmail(HttpServletRequest request) {
        return request.getParameter("email");
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        // 기본 동작을 이메일로 대체
        return obtainEmail(request);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        System.out.println("로그인 성공");

        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) authentication.getPrincipal();

        String email = employeeUserDetails.getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(email, role, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("로그인 실패");
        response.setStatus(401);
    }
}
