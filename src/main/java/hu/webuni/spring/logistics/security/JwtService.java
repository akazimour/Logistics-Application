package hu.webuni.spring.logistics.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {
    public String AUTH = "auth";
    private Algorithm alg = Algorithm.HMAC256("mysecret");
    private String issuer = "LogisticsApp";
    String fullname = "fullname";
    public String createJwtToken(UserDetails principal) {



        return JWT.create()
                .withSubject(principal.getUsername())
                .withArrayClaim(AUTH,principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withClaim(fullname,((User) principal).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(10)))
                .withIssuer(issuer)
                .sign(alg);

    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(alg)
                .withIssuer(issuer)
                .build()
                .verify(jwtToken);
        return new User(decodedJWT.getSubject(),"dummy",decodedJWT.getClaim(AUTH).asList(String.class)
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
