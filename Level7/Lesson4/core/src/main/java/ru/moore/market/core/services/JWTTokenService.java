package ru.moore.market.core.services;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.moore.market.core.interfaces.ITokenService;
import ru.moore.market.core.models.entities.UserInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTTokenService implements ITokenService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public String generateToken(UserInfo user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        String compactTokenString = Jwts.builder()
                .claim("id", user.getUserId())
                .claim("login", user.getLogin())
                .claim("role", user.getRole())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public UserInfo parseToken(String token) throws ExpiredJwtException {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);

        String login = jwsClaims.getBody()
                .getSubject();

        Long userId = jwsClaims.getBody()
                .get("id", Long.class);

        String role = jwsClaims.getBody()
                .get("role", String.class);

        return UserInfo.builder()
                .userId(userId)
                .login(login)
                .role(role)
                .build();
    }
}
