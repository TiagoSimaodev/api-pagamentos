package br.com.apipagamento.exception;

public class PagamentoNotFoundException extends RuntimeException {
	public PagamentoNotFoundException(String message) {
		super(message);
	}
}
