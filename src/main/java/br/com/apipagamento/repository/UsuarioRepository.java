package br.com.apipagamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apipagamento.entity.UsuarioEntity;

public interface  UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	Optional<UsuarioEntity> findByUsername(String username);
}
