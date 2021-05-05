package com.softwarefoundation.ministore.auth.service.impl;

import com.softwarefoundation.ministore.auth.repository.UsuarioRepository;
import com.softwarefoundation.ministore.auth.service.UsuarioService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;

@Primary
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioRepository.findByUsername(username);
        if (Objects.nonNull(usuario)) {
            return usuario;
        }
        throw new UsernameNotFoundException(MessageFormat.format("Usuario {0} não encontrado", username));
    }
}
