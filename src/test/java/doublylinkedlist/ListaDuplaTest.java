package doublylinkedlist;

import br.com.logisticsystem.models.Movimentacao;
import br.com.logisticsystem.models.TipoOperacao;
import br.com.logisticsystem.structures.doublylinkedlist.ListaDupla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListaDuplaTest {


    private ListaDupla<Movimentacao> lista;

    private Movimentacao mov1;
    private Movimentacao mov2;
    private Movimentacao mov3;

    @BeforeEach
    void setUp() {

        lista = new ListaDupla<>();

        mov1 = new Movimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido criado"
        );

        mov2 = new Movimentacao(
                TipoOperacao.ALTERAR_STATUS_ENTREGA,
                "Entrega alterada"
        );

        mov3 = new Movimentacao(
                TipoOperacao.CANCELAR_ENTREGA,
                "Entrega cancelada"
        );
    }


    @Test
    void deveInserirElementosNaLista() {

        lista.inserir(mov1);
        lista.inserir(mov2);

        assertEquals(2, lista.getTamanho());
    }

    @Test
    void deveManterOrdemDeInsercao() {

        lista.inserir(mov1);
        lista.inserir(mov2);
        lista.inserir(mov3);

        assertEquals(mov1, lista.buscar(0));
        assertEquals(mov2, lista.buscar(1));
        assertEquals(mov3, lista.buscar(2));
    }


    @Test
    void deveBuscarElementoPorPosicao() {

        lista.inserir(mov1);
        lista.inserir(mov2);

        assertEquals(mov1, lista.buscar(0));
        assertEquals(mov2, lista.buscar(1));
    }


    @Test
    void deveRemoverElementoIntermediario() {

        lista.inserir(mov1);
        lista.inserir(mov2);
        lista.inserir(mov3);

        Movimentacao removida = lista.remover(1);

        assertEquals(mov2, removida);
        assertEquals(2, lista.getTamanho());

        assertEquals(mov1, lista.buscar(0));
        assertEquals(mov3, lista.buscar(1));
    }

    @Test
    void deveRemoverPrimeiroElemento() {

        lista.inserir(mov1);
        lista.inserir(mov2);

        lista.remover(0);

        assertEquals(1, lista.getTamanho());
        assertEquals(mov2, lista.buscar(0));
    }

    @Test
    void deveRemoverUltimoElemento() {

        lista.inserir(mov1);
        lista.inserir(mov2);

        lista.remover(1);

        assertEquals(1, lista.getTamanho());
        assertEquals(mov1, lista.buscar(0));
    }


    @Test
    void deveListarDoInicioParaOFim() {

        lista.inserir(mov1);
        lista.inserir(mov2);
        lista.inserir(mov3);

        String resultado = lista.listarFrente();

        assertTrue(resultado.contains(mov1.toString()));
        assertTrue(resultado.contains(mov2.toString()));
        assertTrue(resultado.contains(mov3.toString()));
    }

    @Test
    void deveListarDoFimParaOInicio() {

        lista.inserir(mov1);
        lista.inserir(mov2);
        lista.inserir(mov3);

        String resultado = lista.listarTras();

        assertTrue(resultado.contains(mov1.toString()));
        assertTrue(resultado.contains(mov2.toString()));
        assertTrue(resultado.contains(mov3.toString()));
    }


    @Test
    void deveIniciarVazia() {

        assertTrue(lista.isEmpty());
        assertEquals(0, lista.getTamanho());
    }

    @Test
    void deveRetornarFalsoQuandoNaoEstiverVazia() {

        lista.inserir(mov1);

        assertFalse(lista.isEmpty());
    }

    @Test
    void deveControlarTamanhoCorretamente() {

        lista.inserir(mov1);
        lista.inserir(mov2);

        assertEquals(2, lista.getTamanho());

        lista.remover(0);

        assertEquals(1, lista.getTamanho());
    }

}

