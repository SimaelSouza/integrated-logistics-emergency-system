package singlylinkedlist;

import br.com.logisticsystem.exceptions.PosicaoInvalidaException;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumStatus;
import br.com.logisticsystem.models.Pedido;
import br.com.logisticsystem.structures.singlylinkedlist.ListaSimples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListaSimplesTest {

    private ListaSimples<Pedido> lista;

    private Pedido pedido1;
    private Pedido pedido2;
    private Pedido pedido3;

    @BeforeEach
    void setUp() {

        lista = new ListaSimples<>();

        pedido1 = new Pedido("Simael", "Medicamentos", EnumPrioridade.ALTA);

        pedido2 = new Pedido("Arione", "Alimentos", EnumPrioridade.MEDIA);

        pedido3 = new Pedido("Vitor", "Equipamentos", EnumPrioridade.BAIXA);
    }

    @Test
    void deveInserirNoInicioDaLista() {

        lista.inserirNoInicio(pedido1);
        lista.inserirNoInicio(pedido2);

        assertEquals(pedido2, lista.buscar(0));
        assertEquals(pedido1, lista.buscar(1));
        assertEquals(2, lista.getTamanho());
    }

    @Test
    void deveInserirNoFimDaLista() {

        lista.inserirNoFim(pedido1);
        lista.inserirNoFim(pedido2);

        assertEquals(pedido1, lista.buscar(0));
        assertEquals(pedido2, lista.buscar(1));
        assertEquals(2, lista.getTamanho());
    }

    @Test
    void deveInserirEmPosicaoEspecifica() {

        lista.inserirNoFim(pedido1);
        lista.inserirNoFim(pedido3);

        lista.inserir(1, pedido2);

        assertEquals(pedido1, lista.buscar(0));
        assertEquals(pedido2, lista.buscar(1));
        assertEquals(pedido3, lista.buscar(2));
    }

    @Test
    void deveRemoverElementoPorPosicao() {

        lista.inserirNoFim(pedido1);
        lista.inserirNoFim(pedido2);
        lista.inserirNoFim(pedido3);

        Pedido removido = lista.remover(1);

        assertEquals(pedido2, removido);
        assertEquals(2, lista.getTamanho());
    }

    @Test
    void deveBuscarElementoPorPosicao() {

        lista.inserirNoFim(pedido1);
        lista.inserirNoFim(pedido2);

        assertEquals(pedido1, lista.buscar(0));
        assertEquals(pedido2, lista.buscar(1));
    }

    @Test
    void deveAtualizarElementoPorPosicao() {

        lista.inserirNoFim(pedido1);

        lista.atualizar(0, pedido2);

        assertEquals(pedido2, lista.buscar(0));
    }

    @Test
    void deveControlarTamanhoDaLista() {

        assertEquals(0, lista.getTamanho());

        lista.inserirNoFim(pedido1);
        lista.inserirNoFim(pedido2);

        assertEquals(2, lista.getTamanho());

        lista.remover(0);

        assertEquals(1, lista.getTamanho());
    }

    @Test
    void deveListarElementosNaOrdemDaLista() {

        lista.inserirNoFim(pedido1);
        lista.inserirNoFim(pedido2);
        lista.inserirNoFim(pedido3);

        Object[] elementos = lista.listar();

        assertEquals(pedido1, elementos[0]);
        assertEquals(pedido2, elementos[1]);
        assertEquals(pedido3, elementos[2]);
    }

    @Test
    void deveLancarExcecaoAoBuscarPosicaoInvalida() {

        assertThrows(
                PosicaoInvalidaException.class,
                () -> lista.buscar(0)
        );
    }

    @Test
    void deveLancarExcecaoAoInserirEmPosicaoInvalida() {

        assertThrows(
                PosicaoInvalidaException.class,
                () -> lista.inserir(1, pedido1)
        );
    }

    @Test
    void deveLancarExcecaoAoRemoverPosicaoInvalida() {

        assertThrows(
                PosicaoInvalidaException.class,
                () -> lista.remover(0)
        );
    }

    @Test
    void deveLancarExcecaoAoAtualizarPosicaoInvalida() {

        assertThrows(
                PosicaoInvalidaException.class,
                () -> lista.atualizar(0, pedido1)
        );
    }
}