package com.Shadow.JWTProject.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Shadow.JWTProject.models.RefreshToken;
import com.Shadow.JWTProject.repositories.RefreshTokenRepository;
import com.Shadow.JWTProject.repositories.UserRepository;

@Service
public class RefreshTokenService {
    @Value("${ahlem.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    public Long getRefreshTokenDurationMs() {
        return jwtRefreshExpirationMs;
    }

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
}
