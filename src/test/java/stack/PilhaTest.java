package stack;

import br.com.logisticsystem.exceptions.EstruturaVaziaException;
import br.com.logisticsystem.models.Operacao;
import br.com.logisticsystem.models.TipoOperacao;
import br.com.logisticsystem.structures.stack.Pilha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PilhaTest {

    private Pilha<Operacao> pilha;

    private Operacao operacao1;
    private Operacao operacao2;
    private Operacao operacao3;

    @BeforeEach
    void setUp() {
        pilha = new Pilha<>(10);

        UUID uuid = UUID.randomUUID();
        operacao1 = new Operacao(TipoOperacao.CADASTRAR_PEDIDO, uuid, null, "Pedido A", "Pedido A criado");
        operacao2 = new Operacao(TipoOperacao.ALTERAR_ROTA_ENTREGA, uuid, "Pedido A", "Pedido B", "Pedido atualizado para B");
        operacao3 = new Operacao(TipoOperacao.REMOVER_PEDIDO, uuid, "Pedido B", null, "Pedido B removido");
    }

    // ── construtor ─────────────────────────────────────────────────────────────

    @Test
    void deveLancarExcecaoAoCriarPilhaComLimiteZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pilha<>(0)
        );
    }

    @Test
    void deveLancarExcecaoAoCriarPilhaComLimiteNegativo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pilha<>(-1)
        );
    }

    @Test
    void deveIniciarVazia() {
        assertTrue(pilha.isEmpty());
        assertEquals(0, pilha.getTamanho());
    }

    @Test
    void deveRetornarLimiteConfigurado() {
        Pilha<Operacao> pilhaComLimite = new Pilha<>(5);

        assertEquals(5, pilhaComLimite.getLimite());
    }

    // ── push ───────────────────────────────────────────────────────────────────

    @Test
    void deveEmpilharElemento() {
        pilha.push(operacao1);

        assertEquals(1, pilha.getTamanho());
        assertFalse(pilha.isEmpty());
    }

    @Test
    void deveRespeitarOrdemLIFO() {
        pilha.push(operacao1);
        pilha.push(operacao2);
        pilha.push(operacao3);

        assertEquals(operacao3, pilha.pop());
        assertEquals(operacao2, pilha.pop());
        assertEquals(operacao1, pilha.pop());
    }

    @Test
    void deveLancarExcecaoAoEmpilharValorNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pilha.push(null)
        );
    }

    @Test
    void deveLancarExcecaoAoEmpilharComPilhaCheia() {
        Pilha<Operacao> pilhaLimitada = new Pilha<>(2);

        pilhaLimitada.push(operacao1);
        pilhaLimitada.push(operacao2);

        assertThrows(
                IllegalStateException.class,
                () -> pilhaLimitada.push(operacao3)
        );
    }

    @Test
    void deveAtualizarTamanhoAposEmpilhar() {
        pilha.push(operacao1);
        pilha.push(operacao2);

        assertEquals(2, pilha.getTamanho());
    }

    // ── pop ────────────────────────────────────────────────────────────────────

    @Test
    void deveDesempilharElementoDoTopo() {
        pilha.push(operacao1);
        pilha.push(operacao2);

        Operacao removida = pilha.pop();

        assertEquals(operacao2, removida);
        assertEquals(1, pilha.getTamanho());
    }

    @Test
    void deveAtualizarTamanhoAposDesempilhar() {
        pilha.push(operacao1);
        pilha.push(operacao2);

        pilha.pop();

        assertEquals(1, pilha.getTamanho());
    }

    @Test
    void deveLancarExcecaoAoDesempilharComPilhaVazia() {
        assertThrows(
                EstruturaVaziaException.class,
                () -> pilha.pop()
        );
    }

    // ── peek ───────────────────────────────────────────────────────────────────

    @Test
    void deveConsultarTopoSemRemover() {
        pilha.push(operacao1);
        pilha.push(operacao2);

        Operacao topo = pilha.peek();

        assertEquals(operacao2, topo);
        assertEquals(2, pilha.getTamanho());
    }

    @Test
    void deveLancarExcecaoAoConsultarTopoComPilhaVazia() {
        assertThrows(
                EstruturaVaziaException.class,
                () -> pilha.peek()
        );
    }

    // ── listar ─────────────────────────────────────────────────────────────────

    @Test
    void deveListarElementosDoTopoParaBase() {
        pilha.push(operacao1);
        pilha.push(operacao2);
        pilha.push(operacao3);

        Object[] elementos = pilha.listar();

        assertEquals(operacao3, elementos[0]);
        assertEquals(operacao2, elementos[1]);
        assertEquals(operacao1, elementos[2]);
    }

    @Test
    void deveRetornarArrayVazioQuandoPilhaVazia() {
        Object[] elementos = pilha.listar();

        assertEquals(0, elementos.length);
    }

    @Test
    void deveRetornarArrayComTamanhoCorreto() {
        pilha.push(operacao1);
        pilha.push(operacao2);

        Object[] elementos = pilha.listar();

        assertEquals(2, elementos.length);
    }

    // ── limpar ─────────────────────────────────────────────────────────────────

    @Test
    void deveLimparTodosOsElementos() {
        pilha.push(operacao1);
        pilha.push(operacao2);
        pilha.push(operacao3);

        pilha.limpar();

        assertTrue(pilha.isEmpty());
        assertEquals(0, pilha.getTamanho());
    }

    @Test
    void devePermitirEmpilharAposLimpar() {
        pilha.push(operacao1);
        pilha.push(operacao2);

        pilha.limpar();
        pilha.push(operacao3);

        assertEquals(1, pilha.getTamanho());
        assertEquals(operacao3, pilha.peek());
    }

    // ── isEmpty / isFull ───────────────────────────────────────────────────────

    @Test
    void deveRetornarVerdadeiroQuandoPilhaEstiverCheia() {
        Pilha<Operacao> pilhaLimitada = new Pilha<>(2);

        pilhaLimitada.push(operacao1);
        pilhaLimitada.push(operacao2);

        assertTrue(pilhaLimitada.isFull());
    }

    @Test
    void deveRetornarFalsoQuandoPilhaNaoEstiverCheia() {
        pilha.push(operacao1);

        assertFalse(pilha.isFull());
    }

    @Test
    void deveRetornarVerdadeiroParaIsEmptyAposLimpar() {
        pilha.push(operacao1);

        pilha.limpar();

        assertTrue(pilha.isEmpty());
    }
}
