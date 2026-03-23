package br.com.apipagamento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.apipagamento.dto.PagamentoDto;
import br.com.apipagamento.entity.PagamentoEntity;
import br.com.apipagamento.enums.StatusPagamento;
import br.com.apipagamento.repository.PagamentoRepository;
import br.com.apipagamento.service.PagamentoService;

public class TesteService {

    @Test
    void deveCriarPagamento() {
        PagamentoRepository repository = mock(PagamentoRepository.class);

        when(repository.save(any(PagamentoEntity.class))).thenAnswer(invocation -> {
            PagamentoEntity p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        PagamentoService service = new PagamentoService(repository);

        PagamentoDto dto = new PagamentoDto("PIX", BigDecimal.valueOf(100));
        PagamentoEntity pagamento = service.criarPagamento(dto);

        assertNotNull(pagamento.getId());
        assertEquals("PIX", pagamento.getMetodo());
        assertEquals(BigDecimal.valueOf(100), pagamento.getValor());
        assertEquals(StatusPagamento.PENDING, pagamento.getStatus());
    }

    @Test
    void deveAtualizarStatus() {
        PagamentoRepository repository = mock(PagamentoRepository.class);

        PagamentoEntity existente = new PagamentoEntity();
        existente.setId(1L);
        existente.setStatus(StatusPagamento.PENDING);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(PagamentoEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PagamentoService service = new PagamentoService(repository);

        PagamentoEntity atualizado = service.atualizarStatus(1L, "PAID");

        assertEquals(StatusPagamento.PAID, atualizado.getStatus());
    }

    @Test
    void deveBuscarPorId() {
        PagamentoRepository repository = mock(PagamentoRepository.class);

        PagamentoEntity existente = new PagamentoEntity();
        existente.setId(1L);
        existente.setStatus(br.com.apipagamento.enums.StatusPagamento.PENDING);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        PagamentoService service = new PagamentoService(repository);

        PagamentoEntity encontrado = service.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        assertEquals(StatusPagamento.PENDING, encontrado.getStatus());
    }

    @Test
    void deveListarTodos() {
        PagamentoRepository repository = mock(PagamentoRepository.class);

        PagamentoEntity p1 = new PagamentoEntity();
        p1.setId(1L);
        p1.setStatus(StatusPagamento.PENDING);

        PagamentoEntity p2 = new PagamentoEntity();
        p2.setId(2L);
        p2.setStatus(StatusPagamento.PAID);

        List<PagamentoEntity> lista = Arrays.asList(p1, p2);
        when(repository.findAll()).thenReturn(lista);

        PagamentoService service = new PagamentoService(repository);

        List<PagamentoEntity> todos = service.listarTodos();

        assertEquals(2, todos.size());
        assertEquals(StatusPagamento.PENDING, todos.get(0).getStatus());
        assertEquals(StatusPagamento.PAID, todos.get(1).getStatus());
    }
}