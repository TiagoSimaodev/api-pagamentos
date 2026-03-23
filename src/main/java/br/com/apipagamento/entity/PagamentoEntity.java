package br.com.apipagamento.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.apipagamento.enums.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PagamentoEntity implements  Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String metodo;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING) // salva no banco como "PENDING", "PAID" ou "FAILED"
    private StatusPagamento status;

	
	
	public StatusPagamento getStatus() {
		return status;
	}
	public void setStatus(StatusPagamento status) {
		this.status = status;
	}
	private LocalDateTime dataCriacao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
	
	
	
}
