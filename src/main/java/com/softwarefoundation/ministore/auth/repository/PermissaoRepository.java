package com.softwarefoundation.ministore.auth.repository;

import com.softwarefoundation.ministore.auth.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Permissao findByDescricao(@Param("descricao") String descricao);

}
