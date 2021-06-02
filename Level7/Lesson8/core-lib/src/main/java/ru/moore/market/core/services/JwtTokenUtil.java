package ru.moore.market.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.moore.market.core.exceptions.ResourceNotFoundException;
import ru.moore.market.core.models.UserPrincipal;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserPrincipal userPrincipal) {

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + 20 * 60 * 1000);
        return Jwts.builder()
                .claim("user", userPrincipal)
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    public UserPrincipal getUserFromToken(String token) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
        ObjectMapper mapper = new ObjectMapper();
        UserPrincipal userPrincipal = mapper.convertValue(jwsClaims.getBody().get("user"), UserPrincipal.class);
        return userPrincipal;
    }

    public void validateToken(String authToken) throws ResourceNotFoundException {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ResourceNotFoundException("Срок действия токена JWT истек");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
    }
}
