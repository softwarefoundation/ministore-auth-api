package com.softwarefoundation.ministore.auth;

import com.softwarefoundation.ministore.auth.entity.Permissao;
import com.softwarefoundation.ministore.auth.entity.Usuario;
import com.softwarefoundation.ministore.auth.repository.PermissaoRepository;
import com.softwarefoundation.ministore.auth.repository.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class MinistoreAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinistoreAuthApiApplication.class, args);
	}

	private void initUser(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
		Permissao permissao = null;
		Permissao permissaoRetorno = permissaoRepository.findByDescricao("Admin");
		if(Objects.isNull(permissaoRetorno)){
			permissao = new Permissao();
			permissao.setDescricao("Admin");
			permissao = permissaoRepository.save(permissao);
		}else{
			permissao = permissaoRetorno;
		}
		Usuario admin = new Usuario();
		admin.setUsername("admin");
		admin.setPassword(bCryptPasswordEncoder.encode("123"));
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setEnabled(true);
		admin.setPermissoes(Arrays.asList(permissao));
		Usuario usuarioRetorno = usuarioRepository.findByUsername("admin");
		if (Objects.isNull(usuarioRetorno)){
			usuarioRepository.save(admin);
		}
	}

}
