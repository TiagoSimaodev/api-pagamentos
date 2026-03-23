package br.com.apipagamento.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.apipagamento.dto.PagamentoDto;
import br.com.apipagamento.entity.PagamentoEntity;
import br.com.apipagamento.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

	private final PagamentoService pagamentoService;

	// Injeção de dependência via construtor
	public PagamentoController(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}

	// Criar pagamento
	@PostMapping
	public ResponseEntity<PagamentoEntity> criarPagamento(@Valid @RequestBody PagamentoDto dto) {
		PagamentoEntity pagamento = pagamentoService.criarPagamento(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
	}

	// Atualizar status de pagamento
	@PutMapping("/{id}/status")
	public ResponseEntity<PagamentoEntity> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
		// Se não encontrar, o service lança PagamentoNotFoundException
		PagamentoEntity pagamento = pagamentoService.atualizarStatus(id, status);
		return ResponseEntity.ok(pagamento);
	}

	// Buscar pagamento por ID
	@GetMapping("/{id}")
	public ResponseEntity<PagamentoEntity> buscarPorId(@PathVariable Long id) {
		// Se não encontrar, o service lança PagamentoNotFoundException
		PagamentoEntity pagamento = pagamentoService.buscarPorId(id);
		return ResponseEntity.ok(pagamento);
	}

	// Listar todos os pagamentos
	@GetMapping
	public ResponseEntity<List<PagamentoEntity>> listarTodos() {
		List<PagamentoEntity> lista = pagamentoService.listarTodos();
		return ResponseEntity.ok(lista);
	}
}
