package ro.sd.titu.dataapplication.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${secret.key}")
    private String SECRET_KEY;

    public boolean isAuthorizeBasedOnRole(String token) {
        String actualToken = token.substring(7);
        if (isTokenValid(actualToken)) {
            Claims claims = extractAllClaims(actualToken);
            String role = claims.get("roles").toString();
            return role.contains("ADMIN");
        }
        return false;
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        return token != null && !token.isEmpty() && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExtractExpirationDate(token).before(new Date());
    }

    private Date getExtractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

