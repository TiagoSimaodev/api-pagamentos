package br.com.apipagamento.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.apipagamento.dto.PagamentoDto;
import br.com.apipagamento.entity.PagamentoEntity;
import br.com.apipagamento.enums.StatusPagamento;
import br.com.apipagamento.exception.PagamentoNotFoundException;
import br.com.apipagamento.repository.PagamentoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public PagamentoEntity criarPagamento(PagamentoDto dto) {
        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setMetodo(dto.metodo());
        pagamento.setValor(dto.valor());
        pagamento.setStatus(StatusPagamento.PENDING); 
        pagamento.setDataCriacao(LocalDateTime.now());
        return pagamentoRepository.save(pagamento);
    }

    public PagamentoEntity atualizarStatus(Long id, String novoStatus) {
        PagamentoEntity pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNotFoundException("Pagamento não encontrado com id: " + id));

        StatusPagamento statusEnum;
        try {
            statusEnum = StatusPagamento.valueOf(novoStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + novoStatus);
        }

        pagamento.setStatus(statusEnum); 
        return pagamentoRepository.save(pagamento);
    }

    public PagamentoEntity buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNotFoundException("Pagamento não encontrado com o id: " + id));
    }

    public List<PagamentoEntity> listarTodos() {
        return pagamentoRepository.findAll();
    }
}