package com.beyond.twopercent.twofaang.common.service;

import com.beyond.twopercent.twofaang.auth.entity.RefreshToken;
import com.beyond.twopercent.twofaang.auth.repository.RefreshRepository;
import com.beyond.twopercent.twofaang.auth.service.RefreshTokenService;
import com.beyond.twopercent.twofaang.common.entity.AuthCode;
import com.beyond.twopercent.twofaang.common.repository.AuthCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CleanDataService {

    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final RefreshRepository refreshRepository;
    private final AuthCodeRepository authCodeRepository;

    public void cleanRefreshToken() {
        int deleteCnt = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        List<RefreshToken> refreshTokens = refreshTokenService.getAllRefresh();

        for (RefreshToken refreshToken : refreshTokens) {
            try {
                String expirationStr = refreshToken.getExpiration();
                Date expirationDate = formatter.parse(expirationStr);

                if (expirationDate.before(new Date())) {
                    refreshRepository.deleteByRefresh(refreshToken.getRefresh());
                    deleteCnt++;
                }
            } catch (ParseException e) {
                log.error("Failed to parse expiration date: " + refreshToken.getExpiration(), e);
            } catch (Exception e) {
                log.error("Failed to check or delete refresh token: " + refreshToken.getRefresh(), e);
            }
        }

        log.info(deleteCnt + "개의 refreshToken이 삭제 되었습니다.");
    }

    public void cleanAuthCode() {
        int deleteCnt = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        List<AuthCode> authCodes = emailService.getAllAuthCode();

        for (AuthCode authCode : authCodes) {
            try {
                String expirationStr = authCode.getExpiration();
                Date expirationDate = formatter.parse(expirationStr);

                if (expirationDate.before(new Date())) {
                    authCodeRepository.deleteByEmail(authCode.getEmail());
                    deleteCnt++;
                }
            } catch (ParseException e) {
                log.error("Failed to parse expiration date: " + authCode.getExpiration(), e);
            } catch (Exception e) {
                log.error("Failed to check or email auth code: " + authCode.getEmail(), e);
            }
        }

        log.info(deleteCnt + "개의 인증 코드가 삭제 되었습니다.");
    }
}
