package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.services.Infastructure.TokenManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
//Логирование через lombok
@Slf4j
@Component
public class TokenManagerImpl implements TokenManager {
    //Секреты
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;

    public TokenManagerImpl(@Value ("${secret.access}") String accessSecret, @Value("${secret.refresh}") String refreshSecret ){
        this.accessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
        this.refreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(refreshSecret));
    }
    //Генерация access токена
    public String generateJwtToken(@NonNull String email){
        var expire = Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expire)
                .signWith(accessSecret)
//                .claim("roles",account.getRole())
//                .claim("id",account.getId())
                .compact()
                ;
    }
    //Генерация refresh токена
    public String generateRefreshToken(@NonNull String email){
        var expire = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expire)
                .signWith(refreshSecret)
                .compact();
    }
    //Проверка токена
    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }
    //Валидация access токена
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, accessSecret);
    }
    //Валидация refresh токена
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, refreshSecret);
    }
    //Предоставление данных из access токена
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, accessSecret);
    }
    //Предоставление данных из refresh токена
    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, refreshSecret);
    }
    //Предоставление пользовательские данные
    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //Проверка срока жизни refresh token'а
    public boolean checkRefreshExpire(@NonNull String token){
        var claims = getRefreshClaims(token);
        var expire = Calendar.getInstance();
        expire.add(Calendar.HOUR_OF_DAY, -10);
        return claims.getExpiration().getTime() < expire.getTime().getTime();
    }
}
