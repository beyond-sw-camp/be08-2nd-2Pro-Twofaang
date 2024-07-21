package com.beyond.twopercent.twofaang.auth.handler;

import com.beyond.twopercent.twofaang.auth.jwt.JWTUtil;
import com.beyond.twopercent.twofaang.auth.service.RefreshTokenService;
import com.beyond.twopercent.twofaang.auth.util.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 폼 로그인 성공 후 JWT 발급
 * access -> 헤더
 * refresh -> 쿠키
 */
@RequiredArgsConstructor
public class CustomFormSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // create JWT
        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // access
        String access = jwtUtil.createJwt("access", email, role, 60 * 10 * 1000L);
        response.setHeader("access", access);

        // refresh
        Integer expireS = 24 * 60 * 60;
        String refresh = jwtUtil.createJwt("refresh", email, role, expireS * 1000L);
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        // refresh 토큰 DB 저장
        refreshTokenService.saveRefresh(email, expireS, refresh);

        // json 을 ObjectMapper 로 직렬화하여 전달
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("name", email);

        new ObjectMapper().writeValue(response.getWriter(), responseData);
    }
}
