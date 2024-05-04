package com.auth.ProgrammingTechnology.Authorization.services.Infastructure;

import io.jsonwebtoken.Claims;
import lombok.NonNull;

public interface TokenManager {
    public String generateJwtToken(@NonNull String email);
    public String generateRefreshToken(@NonNull String email);
    public boolean validateAccessToken(@NonNull String accessToken);
    public boolean validateRefreshToken(@NonNull String refreshToken);
    public Claims getAccessClaims(@NonNull String token);
    public Claims getRefreshClaims(@NonNull String token);
    public boolean checkRefreshExpire(@NonNull String token);
}
