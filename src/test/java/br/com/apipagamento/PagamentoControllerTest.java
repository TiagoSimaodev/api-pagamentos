package br.com.apipagamento;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.apipagamento.controller.PagamentoController;
import br.com.apipagamento.dto.PagamentoDto;
import br.com.apipagamento.entity.PagamentoEntity;
import br.com.apipagamento.enums.StatusPagamento;
import br.com.apipagamento.service.PagamentoService;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagamentoService pagamentoService;

    @Test
    void deveCriarPagamento() throws Exception {

        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setId(1L);
        pagamento.setMetodo("PIX");
        pagamento.setValor(BigDecimal.valueOf(100));
        pagamento.setStatus(StatusPagamento.PENDING);

        when(pagamentoService.criarPagamento(any(PagamentoDto.class)))
                .thenReturn(pagamento);

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "metodo": "PIX",
                          "valor": 100
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.metodo").value("PIX"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void deveBuscarPorId() throws Exception {

        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setId(1L);
        pagamento.setMetodo("PIX");
        pagamento.setValor(BigDecimal.valueOf(100));
        pagamento.setStatus(StatusPagamento.PAID);

        when(pagamentoService.buscarPorId(1L)).thenReturn(pagamento);

        mockMvc.perform(get("/pagamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    void deveListarTodos() throws Exception {

        PagamentoEntity p1 = new PagamentoEntity();
        p1.setId(1L);
        p1.setStatus(StatusPagamento.PENDING);

        PagamentoEntity p2 = new PagamentoEntity();
        p2.setId(2L);
        p2.setStatus(StatusPagamento.PAID);

        when(pagamentoService.listarTodos()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/pagamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveAtualizarStatus() throws Exception {

        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setId(1L);
        pagamento.setStatus(StatusPagamento.PAID);

        when(pagamentoService.atualizarStatus(1L, "PAID"))
                .thenReturn(pagamento);

        mockMvc.perform(put("/pagamentos/1/status")
                .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }
}
