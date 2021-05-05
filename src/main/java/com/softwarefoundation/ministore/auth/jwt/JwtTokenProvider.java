package com.softwarefoundation.ministore.auth.jwt;

import com.softwarefoundation.ministore.auth.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value(value = "${security.jwt.token.secret-key}")
    private String secretKey;

    @Value(value = "${security.jwt.token.expire-length}")
    private Long expireLength;

    @Autowired
    private UsuarioService usuarioService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles",roles);
        Date dataAtual =  new Date();
        Date dataExpiracao = new Date(dataAtual.getTime() + expireLength);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(dataAtual)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.usuarioService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    private String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
