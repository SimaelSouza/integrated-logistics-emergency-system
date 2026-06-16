package singlylinkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ListaSimplesTest {

    private ListaSimples<String> lista;

    @BeforeEach
    public void setUp() {
        lista = new ListaSimples<>();
    }

    @Test
    public void testListaIniciaVazia() {
        assertTrue(lista.estaVazia());
        assertEquals(0, lista.getTamanho());
    }

    @Test
    public void testInserirNoInicio() {
        lista.inserirNoInicio("Elemento B");
        lista.inserirNoInicio("Elemento A");

        assertEquals(2, lista.getTamanho());
        assertEquals("Elemento A", lista.buscar(0));
        assertEquals("Elemento B", lista.buscar(1));
    }

    @Test
    public void testInserirNoFim() {
        lista.inserirNoFim("Elemento A");
        lista.inserirNoFim("Elemento B");

        assertEquals(2, lista.getTamanho());
        assertEquals("Elemento A", lista.buscar(0));
        assertEquals("Elemento B", lista.buscar(1));
    }

    @Test
    public void testInserirEmPosicaoEspecifica() {
        lista.inserirNoFim("Elemento A"); // Pos 0
        lista.inserirNoFim("Elemento C"); // Pos 1

        // Inserir no meio
        lista.inserir(1, "Elemento B");

        assertEquals(3, lista.getTamanho());
        assertEquals("Elemento A", lista.buscar(0));
        assertEquals("Elemento B", lista.buscar(1));
        assertEquals("Elemento C", lista.buscar(2));
    }

    @Test
    public void testAtualizarElemento() {
        lista.inserirNoFim("Original");
        lista.atualizar(0, "Modificado");

        assertEquals("Modificado", lista.buscar(0));
    }

    @Test
    public void testRemoverDoInicio() {
        lista.inserirNoFim("A");
        lista.inserirNoFim("B");

        String removido = lista.remover(0);

        assertEquals("A", removido);
        assertEquals(1, lista.getTamanho());
        assertEquals("B", lista.buscar(0));
    }

    @Test
    public void testRemoverDoMeioOuFim() {
        lista.inserirNoFim("A");
        lista.inserirNoFim("B");
        lista.inserirNoFim("C");

        String removido = lista.remover(1); // Remove "B"

        assertEquals("B", removido);
        assertEquals(2, lista.getTamanho());
        assertEquals("A", lista.buscar(0));
        assertEquals("C", lista.buscar(1));
    }

    @Test
    public void testLancarExcecaoParaPosicaoInvalidaNaBusca() {
        lista.inserirNoFim("A");

        // Testar índice negativo
        assertThrows(IndexOutOfBoundsException.class, () -> {
            lista.buscar(-1);
        });

        // Testar índice igual ou maior que o tamanho
        assertThrows(IndexOutOfBoundsException.class, () -> {
            lista.buscar(1);
        });
    }

    @Test
    public void testLancarExcecaoParaPosicaoInvalidaNaInsercao() {
        lista.inserirNoFim("A");

        // É permitido inserir na posição igual ao tamanho (equivalente a inserir no fim)
        assertDoesNotThrow(() -> lista.inserir(1, "B"));

        // Não é permitido inserir além do tamanho atual
        assertThrows(IndexOutOfBoundsException.class, () -> {
            lista.inserir(3, "C");
        });
    }

    @Test
    public void testListarNaoQuebraComListaVazia() {
        // Apenas garante que o método roda sem lançar NullPointerException
        assertDoesNotThrow(() -> lista.listar());
    }
}