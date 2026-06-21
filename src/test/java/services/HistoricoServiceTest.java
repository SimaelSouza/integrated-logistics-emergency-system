package services;

import br.com.logisticsystem.models.Movimentacao;
import br.com.logisticsystem.models.TipoOperacao;
import br.com.logisticsystem.services.HistoricoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoricoServiceTest {

    private HistoricoService historicoService;

    @BeforeEach
    void setUp() {
        historicoService = new HistoricoService();
    }


    @Test
    void deveRegistrarMovimentacao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado"
        );

        assertEquals(
                1,
                historicoService.getQuantidadeMovimentacoes()
        );
    }

    @Test
    void deveRegistrarMovimentacaoComEntidade() {

        Object entidade = "Pedido A";

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado",
                entidade
        );

        Movimentacao mov =
                historicoService.buscarPorPosicao(0);

        assertEquals(
                entidade,
                mov.getEntidadeEnvolvida()
        );
    }


    @Test
    void deveIniciarVazio() {

        assertTrue(
                historicoService.estaVazio()
        );
    }

    @Test
    void naoDeveEstarVazioAposRegistro() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado"
        );

        assertFalse(
                historicoService.estaVazio()
        );
    }


    @Test
    void deveBuscarMovimentacaoPorId() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado"
        );

        Movimentacao mov =
                historicoService.buscarPorPosicao(0);

        Movimentacao encontrada =
                historicoService.buscarPorId(
                        mov.getId()
                );

        assertEquals(
                mov,
                encontrada
        );
    }

    @Test
    void deveRetornarNullAoBuscarIdInexistente() {

        assertNull(
                historicoService.buscarPorId(
                        java.util.UUID.randomUUID()
                )
        );
    }


    @Test
    void deveBuscarPorPosicao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado"
        );

        Movimentacao mov =
                historicoService.buscarPorPosicao(0);

        assertNotNull(mov);
    }


    @Test
    void deveBuscarPorTipoOperacao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido A"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido B"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Pedido removido"
        );

        Movimentacao[] resultado =
                historicoService.buscarPorTipo(
                        TipoOperacao.CADASTRAR_PEDIDO
                );

        assertEquals(2, resultado.length);
    }

    @Test
    void deveRetornarArrayVazioQuandoNaoExistirTipo() {

        Movimentacao[] resultado =
                historicoService.buscarPorTipo(
                        TipoOperacao.REMOVER_PEDIDO
                );

        assertEquals(0, resultado.length);
    }


    @Test
    void deveBuscarMovimentacoesPorPeriodo() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido"
        );

        LocalDateTime inicio =
                LocalDateTime.now().minusMinutes(1);

        LocalDateTime fim =
                LocalDateTime.now().plusMinutes(1);

        Movimentacao[] resultado =
                historicoService.buscarPorPeriodo(
                        inicio,
                        fim
                );

        assertEquals(1, resultado.length);
    }


    @Test
    void deveRetornarPrimeiraMovimentacao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Primeira"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Segunda"
        );

        Movimentacao primeira =
                historicoService.primeiraMovimentacao();

        assertEquals(
                "Primeira",
                primeira.getDescricao()
        );
    }

    @Test
    void deveRetornarUltimaMovimentacao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Primeira"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Segunda"
        );

        Movimentacao ultima =
                historicoService.ultimaMovimentacao();

        assertEquals(
                "Segunda",
                ultima.getDescricao()
        );
    }

    @Test
    void deveNavegarParaProximaMovimentacao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Primeira"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Segunda"
        );

        historicoService.primeiraMovimentacao();

        Movimentacao proxima =
                historicoService.proximaMovimentacao();

        assertEquals(
                "Segunda",
                proxima.getDescricao()
        );
    }

    @Test
    void deveNavegarParaMovimentacaoAnterior() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Primeira"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Segunda"
        );

        historicoService.ultimaMovimentacao();

        Movimentacao anterior =
                historicoService.movimentacaoAnterior();

        assertEquals(
                "Primeira",
                anterior.getDescricao()
        );
    }


    @Test
    void deveListarHistorico() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido A"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Pedido B"
        );

        assertEquals(
                2,
                historicoService.listarHistorico().length
        );
    }


    @Test
    void deveOrdenarPorDescricao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Zeta"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Alpha"
        );

        Movimentacao[] ordenadas =
                historicoService.listarOrdenadoPorDescricao();

        assertEquals(
                "Alpha",
                ordenadas[0].getDescricao()
        );
    }

    @Test
    void deveOrdenarPorTipoOperacao() {

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Remoção"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Cadastro"
        );

        Movimentacao[] ordenadas =
                historicoService.listarOrdenadoPorTipoOperacao();

        assertTrue(
                ordenadas[0].getTipoOperacao()
                        .name()
                        .compareTo(
                                ordenadas[1]
                                        .getTipoOperacao()
                                        .name()
                        ) <= 0
        );
    }

    @Test
    void deveOrdenarPorHorario() throws InterruptedException {

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Primeira"
        );

        Thread.sleep(5);

        historicoService.registrarMovimentacao(
                TipoOperacao.REMOVER_PEDIDO,
                "Segunda"
        );

        Movimentacao[] ordenadas =
                historicoService.listarOrdenadoPorHorario();

        assertEquals(
                "Primeira",
                ordenadas[0].getDescricao()
        );

        assertEquals(
                "Segunda",
                ordenadas[1].getDescricao()
        );
    }
}