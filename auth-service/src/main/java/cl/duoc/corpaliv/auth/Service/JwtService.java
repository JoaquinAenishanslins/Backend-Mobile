package cl.duoc.corpaliv.auth.Service;

import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final Key key;

    public JwtService(@Value("${jwt.secret:esta_es_mi_compleja_clave_secreta}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {

        if (token == null || token.isBlank()) return null; {
        String jwt = token.startsWith("bearer ") ? token.substring(7) : token;
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    }       
    
    public boolean isValid(String token) {
        if (token == null || token.isBlank()) return false; {
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
            try {
                Jwts.parser()
                        .verifyWith((SecretKey) key)
                        .build()
                        .parseSignedClaims(jwt);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        } 



    

    }
}

