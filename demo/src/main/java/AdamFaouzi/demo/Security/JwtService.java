package AdamFaouzi.demo.Security;

import AdamFaouzi.demo.Entities.AppUser;
import AdamFaouzi.demo.Services.AppUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final String ENCRYPTION_KEY = "8cd3d16e3ffea69c5f34b3d563899e0212f45e000c72bb749c746c20eaa6614c";
    private AppUserService appUserService;
    private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;


    public Map<String, String> generate(String username){
        AppUser appUser = (AppUser) this.appUserService.loadUserByUsername(username);
        return this.generateJwt(appUser);
    }

    public String extractUsername (String token){
        return this.getClaim(token, Claims::getSubject);
    }

    public String extractId(String token){
        return this.getClaim(token, Claims::getId);
    }

    public boolean isTokenExpired(String token){
        Date expirationDate = getExpirationDateFromToken(token);

        if (expirationDate == null) {
            return true;
        }

        long allowedClockSkewMillis = 10000;

        return expirationDate.toInstant().minusMillis(allowedClockSkewMillis).isBefore(Instant.now());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(String token, Function<Claims, T>function){
        Claims claims = getAllClaims(token);
        System.out.println("Token verified. Expiration: " + claims.getExpiration());
        return function.apply(claims);
    }

    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Map<String, String> generateJwt(AppUser appUser) {

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + EXPIRATION_TIME_MS;

        System.out.println("Current Time: " + new Date(currentTime));
        System.out.println("Expiration Time: " + new Date(expirationTime));

        final Map<String, Object> claims = Map.of(
                "First name", appUser.getFirstName(),
                "Last name", appUser.getLastName(),
                "Role", appUser.getRole(),
                Claims.ID, appUser.getId(),
                Claims.SUBJECT, appUser.getEmail(),
                Claims.EXPIRATION, new Date(expirationTime)
        );


        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(appUser.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }


}
