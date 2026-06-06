package queue;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.structures.queue.Fila;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilaTest {

    private Fila fila;
    private Atendimento atendimento1;
    private Atendimento atendimento2;
    private Atendimento atendimento3;

    @BeforeEach
    void setUp() {
        fila = new Fila(10);

        atendimento1 = new Atendimento("Simael", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.ATRASO_ENTREGA);
        atendimento2 = new Atendimento("Arione", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.SOLICITACAO_SUPORTE);
        atendimento3 = new Atendimento("Vitor", EnumPrioridade.SEM_PRIORIDADE, EnumTipo.VAZAMENTO
        );
    }

    @Test
    void deveInserirElementosNaFila() {
        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);

        assertEquals(2, fila.getTamanho());
    }

    @Test
    void deveRespeitarOrdemFIFO() {
        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);
        fila.enqueue(atendimento3);

        assertEquals(atendimento1, fila.dequeue());
        assertEquals(atendimento2, fila.dequeue());
        assertEquals(atendimento3, fila.dequeue());
    }

    @Test
    void deveRemoverPrimeiroElemento() {
        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);

        Atendimento removido = fila.dequeue();

        assertEquals(atendimento1, removido);
        assertEquals(1, fila.getTamanho());
    }

    @Test
    void deveConsultarPrimeiroSemRemover() {
        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);

        Atendimento primeiro = fila.peek();

        assertEquals(atendimento1, primeiro);
        assertEquals(2, fila.getTamanho());
    }

    @Test
    void deveIniciarVazia() {
        assertTrue(fila.isEmpty());
        assertEquals(0, fila.getTamanho());
    }

    @Test
    void naoDeveEstarVaziaAposInsercao() {
        fila.enqueue(atendimento1);

        assertFalse(fila.isEmpty());
    }

    @Test
    void deveAtualizarTamanhoCorretamente() {
        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);

        assertEquals(2, fila.getTamanho());

        fila.dequeue();

        assertEquals(1, fila.getTamanho());
    }

    @Test
    void deveListarElementosNaOrdemDaFila() {
        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);
        fila.enqueue(atendimento3);

        Atendimento[] elementos = fila.listar();

        assertEquals(atendimento1, elementos[0]);
        assertEquals(atendimento2, elementos[1]);
        assertEquals(atendimento3, elementos[2]);
    }

    @Test
    void naoDeveInserirQuandoFilaEstiverCheia() {
        Fila fila = new Fila(2);

        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);

        assertThrows(
                IllegalStateException.class,
                () -> fila.enqueue(atendimento3)
        );
    }

    @Test
    void deveRetornarVerdadeiroQuandoFilaEstiverCheia() {
        Fila fila = new Fila(2);

        fila.enqueue(atendimento1);
        fila.enqueue(atendimento2);

        assertTrue(fila.isFull());
    }

    @Test
    void deveRetornarFalsoQuandoFilaNaoEstiverCheia() {
        fila.enqueue(atendimento1);

        assertFalse(fila.isFull());
    }
}
