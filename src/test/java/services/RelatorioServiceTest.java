package services;

import br.com.logisticsystem.models.Relatorio;
import br.com.logisticsystem.models.TipoOperacao;
import br.com.logisticsystem.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioServiceTest {

    private EstatisticaService estatisticaService;
    private HistoricoService historicoService;
    private AtendimentoService atendimentoService;
    private PedidoService pedidoService;
    private EntregaService entregaService;
    private RelatorioService relatorioService;

    @BeforeEach
    void setUp() {
        historicoService = new HistoricoService();
        atendimentoService = new AtendimentoService(historicoService);
        pedidoService = new PedidoService(historicoService);
        entregaService = new EntregaService(historicoService);

        estatisticaService = new EstatisticaService(atendimentoService, pedidoService, entregaService, historicoService);


        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado"
        );

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_ENTREGA,
                "Entrega criada"
        );

        relatorioService =
                new RelatorioService(
                        estatisticaService,
                        historicoService
                );
    }

    @Test
    void deveGerarRelatorioCompleto() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertNotNull(relatorio);
    }

    @Test
    void devePreencherBlocoDeAtendimentos() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertTrue(
                relatorio.getTotalAtendimentosPendentes() >= 0
        );

        assertTrue(
                relatorio.getTempoMedioEsperaSegundos() >= 0
        );
    }

    @Test
    void devePreencherBlocoDePedidos() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertTrue(
                relatorio.getTotalPedidos() >= 0
        );

        assertTrue(
                relatorio.getPedidosAtivos() >= 0
        );
    }

    @Test
    void devePreencherBlocoDeEntregas() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertTrue(
                relatorio.getTotalEntregas() >= 0
        );

        assertTrue(
                relatorio.getEntregasConcluidas() >= 0
        );

        assertTrue(
                relatorio.getEntregasCanceladas() >= 0
        );

        assertTrue(
                relatorio.getTempoMedioEntregaHoras() >= 0
        );
    }

    @Test
    void devePreencherQuantidadeMovimentacoes() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertEquals(
                historicoService.getQuantidadeMovimentacoes(),
                relatorio.getQuantidadeOperacoes()
        );
    }

    @Test
    void deveDefinirAlgoritmoMaisRapido() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertNotNull(
                relatorio.getAlgoritmoMaisRapido()
        );

        assertFalse(
                relatorio.getAlgoritmoMaisRapido().isBlank()
        );
    }

    @Test
    void deveDefinirEstruturaMaisEficiente() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertNotNull(
                relatorio.getEstruturaMaisEficiente()
        );

        assertFalse(
                relatorio.getEstruturaMaisEficiente().isBlank()
        );
    }

    @Test
    void deveCalcularTempoMedioProcessamento() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertTrue(
                relatorio.getTempoMedioProcessamentoMs() > 0
        );
    }

    @Test
    void deveCalcularTamanhoMedioFilas() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertTrue(
                relatorio.getTamanhoMedioFilas() >= 0
        );
    }

    @Test
    void deveCalcularTaxaCrescimentoSistema() {

        Relatorio relatorio =
                relatorioService.gerarRelatorioCompleto();

        assertTrue(
                relatorio.getTaxaCrescimentoSistema() >= 0
        );
    }

    @Test
    void deveGerarRelatoriosConsecutivos() {

        Relatorio primeiro =
                relatorioService.gerarRelatorioCompleto();

        Relatorio segundo =
                relatorioService.gerarRelatorioCompleto();

        assertNotNull(primeiro);
        assertNotNull(segundo);
    }
}
