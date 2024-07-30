package com.beyond.twopercent.twofaang.auth.handler;


import com.beyond.twopercent.twofaang.auth.dto.oAuth2.CustomOAuth2Member;
import com.beyond.twopercent.twofaang.auth.jwt.JWTUtil;
import com.beyond.twopercent.twofaang.auth.service.RefreshTokenService;
import com.beyond.twopercent.twofaang.auth.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // create JWT
        CustomOAuth2Member customOAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();

        String name = customOAuth2Member.getName(); // 실제 이름
        String email = customOAuth2Member.getEmail(); // DB 저장용 식별자
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // access
        Integer expireA = 60 * 10;  // 10분
        String access = jwtUtil.createJwt("access", email, role, expireA * 1000L);
        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));

        // refresh
        Integer expireS = 60 * 30;  // 30분
        String refresh = jwtUtil.createJwt("refresh", email, role, expireS * 1000L);
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        // refresh 토큰 DB 저장
        refreshTokenService.saveRefresh(email, expireS, refresh);

        // equals
        if (role.contains("ADMIN")) {
            response.sendRedirect("http://localhost:8080/admin/main.do");
        } else {
            response.sendRedirect("http://localhost:8080/");
        }
    }
}
