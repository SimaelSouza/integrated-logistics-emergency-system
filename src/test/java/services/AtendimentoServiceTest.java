package services;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.services.AtendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoServiceTest {

    private AtendimentoService service;

    @BeforeEach
    void setUp() {
        service = new AtendimentoService(10);
    }

    // ── cadastrar ──────────────────────────────────────────────────────────────

    @Test
    void deveCadastrarAtendimentoComPrioridadeAlta() {
        Atendimento atendimento = service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        assertNotNull(atendimento);
        assertEquals("Simael", atendimento.getNomeSolicitante());
        assertEquals(EnumPrioridade.ALTA, atendimento.getPrioridade());
        assertEquals(EnumTipo.ACIDENTE, atendimento.getTipoOcorrencia());
    }

    @Test
    void deveCadastrarAtendimentoSemPrioridadeNaFilaComum() {
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        assertEquals(1, service.listarFilaComum().length);
        assertEquals(0, service.listarFilaPrioritaria().length);
    }

    @Test
    void deveCadastrarAtendimentoComPrioridadeNaFilaPrioritaria() {
        service.cadastrar("Vitor", EnumPrioridade.MEDIA, EnumTipo.VAZAMENTO);

        assertEquals(0, service.listarFilaComum().length);
        assertEquals(1, service.listarFilaPrioritaria().length);
    }

    @Test
    void deveLancarExcecaoAoCadastrarComNomeNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar(null, EnumPrioridade.ALTA, EnumTipo.ACIDENTE)
        );
    }

    @Test
    void deveLancarExcecaoAoCadastrarComNomeEmBranco() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar("   ", EnumPrioridade.ALTA, EnumTipo.ACIDENTE)
        );
    }

    @Test
    void deveLancarExcecaoAoCadastrarComPrioridadeNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar("Simael", null, EnumTipo.ACIDENTE)
        );
    }

    @Test
    void deveLancarExcecaoAoCadastrarComTipoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar("Simael", EnumPrioridade.ALTA, null)
        );
    }

    // ── atenderProximo ─────────────────────────────────────────────────────────

    @Test
    void deveAtenderPrioritarioAntesDoComum() {
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);
        Atendimento prioritario = service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        Atendimento atendido = service.atenderProximo();

        assertEquals(prioritario, atendido);
    }

    @Test
    void deveAtenderFilaComumQuandoNaoHaPrioritarios() {
        Atendimento comum = service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        Atendimento atendido = service.atenderProximo();

        assertEquals(comum, atendido);
    }

    @Test
    void deveRetornarNuloQuandoAmbasAsFilasEstiveremVazias() {
        Atendimento atendido = service.atenderProximo();

        assertNull(atendido);
    }

    @Test
    void deveIncrementarTotalAtendimentosAposAtender() {
        service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        service.atenderProximo();
        service.atenderProximo();

        assertEquals(2, service.getTotalAtendimentos());
    }

    @Test
    void deveReduzirTotalPendentesAposAtender() {
        service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        service.atenderProximo();

        assertEquals(1, service.getTotalPendentes());
    }

    // ── alterarPrioridade ──────────────────────────────────────────────────────

    @Test
    void deveAlterarPrioridadeDeComumParaPrioritario() {
        Atendimento atendimento = service.cadastrar("Simael", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        boolean resultado = service.alterarPrioridade(atendimento, EnumPrioridade.ALTA);

        assertTrue(resultado);
        assertEquals(EnumPrioridade.ALTA, atendimento.getPrioridade());
        assertEquals(0, service.listarFilaComum().length);
        assertEquals(1, service.listarFilaPrioritaria().length);
    }

    @Test
    void deveAlterarPrioridadeDePrioritarioParaComum() {
        Atendimento atendimento = service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        boolean resultado = service.alterarPrioridade(atendimento, EnumPrioridade.SEM_PRIORIDADE);

        assertTrue(resultado);
        assertEquals(EnumPrioridade.SEM_PRIORIDADE, atendimento.getPrioridade());
        assertEquals(1, service.listarFilaComum().length);
        assertEquals(0, service.listarFilaPrioritaria().length);
    }

    @Test
    void deveRetornarFalsoAoAlterarPrioridadeDeAtendimentoInexistente() {
        Atendimento foraDeQualquerFila = new Atendimento("Vitor", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        boolean resultado = service.alterarPrioridade(foraDeQualquerFila, EnumPrioridade.MEDIA);

        assertFalse(resultado);
    }

    @Test
    void deveLancarExcecaoAoAlterarParaPrioridadeNula() {
        Atendimento atendimento = service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.alterarPrioridade(atendimento, null)
        );
    }

    // ── consultarProximo ───────────────────────────────────────────────────────

    @Test
    void deveConsultarProximoSemRemoverDaFila() {
        service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        service.consultarProximo();

        assertEquals(1, service.getTotalPendentes());
    }

    @Test
    void deveConsultarPrioritarioComoProximoQuandoExistir() {
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);
        Atendimento prioritario = service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);

        Atendimento proximo = service.consultarProximo();

        assertEquals(prioritario, proximo);
    }

    // ── estado geral ───────────────────────────────────────────────────────────

    @Test
    void deveIniciarComFilasVazias() {
        assertTrue(service.filaVazia());
        assertEquals(0, service.getTotalPendentes());
        assertEquals(0, service.getTotalAtendimentos());
    }

    @Test
    void deveRetornarFilaVaziaAposAtenderTodos() {
        service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        service.atenderProximo();
        service.atenderProximo();

        assertTrue(service.filaVazia());
    }

    @Test
    void deveCalcularTempoMedioZeroSemAtendimentos() {
        assertEquals(0.0, service.calcularTempoMedioEsperaSegundos());
    }

    @Test
    void deveCalcularTempoMedioAposAtendimentos() {
        service.cadastrar("Simael", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);
        service.cadastrar("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);

        service.atenderProximo();
        service.atenderProximo();

        assertTrue(service.calcularTempoMedioEsperaSegundos() >= 0.0);
    }
}
