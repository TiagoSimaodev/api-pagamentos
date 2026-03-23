package br.com.apipagamento.dto;

import java.math.BigDecimal;

public record PagamentoDto(String metodo, BigDecimal valor) {

}
