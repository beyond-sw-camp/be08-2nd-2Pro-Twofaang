package com.beyond.twopercent.twofaang.common.service;


import com.beyond.twopercent.twofaang.auth.entity.RefreshToken;
import com.beyond.twopercent.twofaang.auth.jwt.JWTUtil;
import com.beyond.twopercent.twofaang.auth.repository.RefreshRepository;
import com.beyond.twopercent.twofaang.auth.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CleanDataService {

    private final RefreshTokenService refreshTokenService;
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

    public void CleanRefreshToken() {
        int deleteCnt = 0;

        List<RefreshToken> refreshTokens = refreshTokenService.getAllRefresh();

        for (RefreshToken refreshToken : refreshTokens) {
            if(jwtUtil.isExpired(refreshToken.getRefresh())) {
                refreshRepository.deleteByRefresh(refreshToken.getRefresh());
                deleteCnt++;
            }
        }

        log.info(deleteCnt+"개의 refreshToken이 삭제 되었습니다.");
    }

}
