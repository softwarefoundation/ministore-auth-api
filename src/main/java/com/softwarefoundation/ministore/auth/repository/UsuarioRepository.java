package com.softwarefoundation.ministore.auth.repository;

import com.softwarefoundation.ministore.auth.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(@Param("username") String username);

}
