package br.com.apipagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Indica que essa classe vai tratar exceções globalmente
@ControllerAdvice
public class GlobalExceptionHandler {

	// Trata exceções de Pagamento não encontrado
	@ExceptionHandler(PagamentoNotFoundException.class)
	public ResponseEntity<String> handlePagamentoNotFound(PagamentoNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	// Trata outras exceções genéricas
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		// Aqui você pode logar o erro, se quiser
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Ocorreu um erro inesperado: " + ex.getMessage());
	}
}
