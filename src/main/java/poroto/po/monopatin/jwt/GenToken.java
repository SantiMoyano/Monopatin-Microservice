package poroto.po.monopatin.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GenToken {

    private static final String SECRET_KEY = "ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas en milisegundos

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }
    

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
