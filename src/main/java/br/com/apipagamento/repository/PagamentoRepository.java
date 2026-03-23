package br.com.apipagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apipagamento.entity.PagamentoEntity;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

}
