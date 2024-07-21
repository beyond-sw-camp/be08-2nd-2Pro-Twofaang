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
import java.net.URLEncoder;

/**
 * OAuth2 로그인 성공 후 JWT 발급
 * access, refresh -> httpOnly 쿠키
 * 리다이렉트 되기 때문에 헤더로 전달 불가능
 */
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

        Integer expireS = 24 * 60 * 60;
        String access = jwtUtil.createJwt("access", email, role, 60 * 10 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", email, role, expireS * 1000L);

        // refresh 토큰 DB 저장
        refreshTokenService.saveRefresh(email, expireS, refresh);

        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        // redirect query param 인코딩 후 전달
        // 이후에 JWT 를 읽어서 데이터를 가져올 수도 있지만, JWT 파싱 비용이 많이 들기 때문에
        // 처음 JWT 발급할 때 이름을 함께 넘긴 후, 로컬 스토리지에 저장한다.
        String encodedName = URLEncoder.encode(name, "UTF-8");
        response.sendRedirect("http://localhost:8080/");
    }
}
