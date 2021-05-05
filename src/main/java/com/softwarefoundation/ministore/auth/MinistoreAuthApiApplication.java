package com.softwarefoundation.ministore.auth;

import com.softwarefoundation.ministore.auth.entity.Permissao;
import com.softwarefoundation.ministore.auth.entity.Usuario;
import com.softwarefoundation.ministore.auth.repository.PermissaoRepository;
import com.softwarefoundation.ministore.auth.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Objects;
@Slf4j
@SpringBootApplication
public class MinistoreAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinistoreAuthApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
		return args -> initUser(usuarioRepository,permissaoRepository,bCryptPasswordEncoder);
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
		log.info("Password: {}", admin.getPassword());
		if (Objects.isNull(usuarioRetorno)){
			usuarioRepository.save(admin);
		}
	}

}
