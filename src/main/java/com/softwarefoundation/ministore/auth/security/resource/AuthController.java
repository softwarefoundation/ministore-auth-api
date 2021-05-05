package com.softwarefoundation.ministore.auth.security.resource;

import com.softwarefoundation.ministore.auth.dto.UsuarioDto;
import com.softwarefoundation.ministore.auth.jwt.JwtTokenProvider;
import com.softwarefoundation.ministore.auth.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> login(@RequestBody UsuarioDto usuarioDto){
        try{
            var username = usuarioDto.getUsername();
            var password = usuarioDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = usuarioRepository.findByUsername(username);
            var token = "";
            if(Objects.nonNull(user)){
                token = jwtTokenProvider.createToken(username, user.getRoles());
            }else {
                throw new UsernameNotFoundException("Username not found");
            }
            Map<Object,Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        }catch (Exception e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @RequestMapping("/teste")
    public String teste(){
        return "OK";
    }

}
