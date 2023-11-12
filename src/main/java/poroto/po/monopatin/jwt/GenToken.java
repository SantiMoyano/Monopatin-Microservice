package poroto.po.monopatin.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenToken {

    // private static final String SECRET_KEY = "ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10ClaveSecretaX10";
    // private static final long EXPIRATION_TIME = 86400000; // 24 horas en milisegundos

    public String generateToken(String username) {
        // return Jwts.builder()
            // .setSubject(username)
            // .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            // .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            // .compact();

        	String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
    }
    

    // public String getUsernameFromToken(String token) {
        // return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    // }

    // public boolean validateToken(String token) {
    //     try {
    //         Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    //         return true;
    //     } catch (Exception e) {
    //         return false;
    //     }
    // }

    // private String getJWTToken(String username) {
	// 	String secretKey = "mySecretKey";
	// 	List<GrantedAuthority> grantedAuthorities = AuthorityUtils
	// 			.commaSeparatedStringToAuthorityList("ROLE_USER");
		
	// 	String token = Jwts
	// 			.builder()
	// 			.setId("softtekJWT")
	// 			.setSubject(username)
	// 			.claim("authorities",
	// 					grantedAuthorities.stream()
	// 							.map(GrantedAuthority::getAuthority)
	// 							.collect(Collectors.toList()))
	// 			.setIssuedAt(new Date(System.currentTimeMillis()))
	// 			.setExpiration(new Date(System.currentTimeMillis() + 600000))
	// 			.signWith(SignatureAlgorithm.HS512,
	// 					secretKey.getBytes()).compact();

	// 	return "Bearer " + token;
	// }
}
