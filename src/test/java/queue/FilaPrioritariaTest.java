package queue;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.structures.queue.FilaPrioritaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilaPrioritariaTest {

    private FilaPrioritaria fila;

    private Atendimento baixa;
    private Atendimento media;
    private Atendimento alta;

    @BeforeEach
    void setUp() {

        fila = new FilaPrioritaria(10);

        baixa = new Atendimento("Simael", EnumPrioridade.BAIXA, EnumTipo.ATRASO_ENTREGA);
        media = new Atendimento("Arione", EnumPrioridade.MEDIA, EnumTipo.VAZAMENTO);
        alta = new Atendimento("Vitor", EnumPrioridade.ALTA, EnumTipo.ACIDENTE);
    }

    @Test
    void deveInserirAtendimentoNaFila() {

        fila.enqueue(alta);

        assertEquals(1, fila.getTamanho());
    }

    @Test
    void deveIniciarVazia() {

        assertTrue(fila.isEmpty());
        assertEquals(0, fila.getTamanho());
    }

    @Test
    void naoDeveEstarVaziaAposInsercao() {

        fila.enqueue(alta);

        assertFalse(fila.isEmpty());
    }

    @Test
    void deveAtualizarTamanhoCorretamente() {

        fila.enqueue(alta);
        fila.enqueue(media);

        assertEquals(2, fila.getTamanho());

        fila.dequeue();

        assertEquals(1, fila.getTamanho());
    }

    @Test
    void deveRetornarElementoDeMaiorPrioridadePrimeiro() {

        fila.enqueue(baixa);
        fila.enqueue(alta);
        fila.enqueue(media);

        assertEquals(alta, fila.dequeue());
        assertEquals(media, fila.dequeue());
        assertEquals(baixa, fila.dequeue());
    }

    @Test
    void deveRespeitarFIFOEntreMesmasPrioridades() {

        Atendimento alta1 = new Atendimento("Alta 1", EnumPrioridade.ALTA, EnumTipo.INCENDIO);
        Atendimento alta2 = new Atendimento("Alta 2", EnumPrioridade.ALTA, EnumTipo.ROUBO_CARGA);

        fila.enqueue(alta1);
        fila.enqueue(alta2);

        assertEquals(alta1, fila.dequeue());
        assertEquals(alta2, fila.dequeue());
    }

    @Test
    void deveConsultarMaiorPrioridadeSemRemover() {

        fila.enqueue(baixa);
        fila.enqueue(alta);

        Atendimento primeiro = fila.peek();

        assertEquals(alta, primeiro);
        assertEquals(2, fila.getTamanho());
    }

    @Test
    void deveListarElementosOrdenadosPorPrioridade() {

        fila.enqueue(baixa);
        fila.enqueue(alta);
        fila.enqueue(media);

        Atendimento[] atendimentos = fila.listar();

        assertEquals(alta, atendimentos[0]);
        assertEquals(media, atendimentos[1]);
        assertEquals(baixa, atendimentos[2]);
    }

    @Test
    void naoDeveInserirQuandoFilaEstiverCheia() {

        FilaPrioritaria fila = new FilaPrioritaria(2);

        fila.enqueue(alta);
        fila.enqueue(media);

        assertThrows(
                IllegalStateException.class,
                () -> fila.enqueue(baixa)
        );
    }

    @Test
    void deveRetornarVerdadeiroQuandoFilaEstiverCheia() {

        FilaPrioritaria fila = new FilaPrioritaria(2);

        fila.enqueue(alta);
        fila.enqueue(media);

        assertTrue(fila.isFull());
    }

    @Test
    void deveRetornarFalsoQuandoFilaNaoEstiverCheia() {

        fila.enqueue(alta);

        assertFalse(fila.isFull());
    }
}
