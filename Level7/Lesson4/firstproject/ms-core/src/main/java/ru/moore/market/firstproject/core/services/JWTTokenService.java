package ru.moore.market.firstproject.core.services;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.moore.market.firstproject.core.interfaces.ITokenService;
import ru.moore.market.firstproject.core.interfaces.TokenRepository;
import ru.moore.market.firstproject.core.models.entities.SecurityToken;
import ru.moore.market.firstproject.core.models.entities.UserInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:module_core.properties")
@CacheConfig(cacheNames = "tokenCache")
public class JWTTokenService implements ITokenService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Autowired
    private TokenRepository tokenRepository;

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

        Date timeToLife = jwsClaims.getBody()
                .get("exp", Date.class);

        return UserInfo.builder()
                .userId(userId)
                .login(login)
                .role(role)
                .timeToLive(timeToLife)
                .build();
    }

    @CacheEvict(cacheNames = "token", allEntries = true)
    public SecurityToken addInvalidToken(SecurityToken customer) {
        return tokenRepository.save(customer);
    }

    @Cacheable(cacheNames = "token")
    public boolean findByToken(String token) {
        Iterable<SecurityToken> listSecurityToken = tokenRepository.findAll();
        for (SecurityToken temp : listSecurityToken) {
            if (temp.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }
}
