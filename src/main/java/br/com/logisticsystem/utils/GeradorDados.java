package br.com.logisticsystem.utils;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.Entrega;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumStatus;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.models.Pedido;
import br.com.logisticsystem.models.Rota;

import java.util.Random;

public class GeradorDados {

    private static final Random random = new Random();

    private static final String[] NOMES = {
            "Ana Silva", "Bruno Costa", "Carlos Souza", "Diana Lima",
            "Eduardo Pereira", "Fernanda Santos", "Gabriel Oliveira", "Helena Rocha",
            "Igor Martins", "Julia Ferreira", "Leonardo Alves", "Mariana Castro",
            "Nicolas Ribeiro", "Olivia Mendes", "Paulo Carvalho", "Rafaela Nunes"
    };

    private static final String[] DESCRICOES_CARGA = {
            "Eletrônicos frágeis", "Medicamentos refrigerados", "Documentos urgentes",
            "Peças automotivas", "Alimentos perecíveis", "Equipamentos industriais",
            "Material de construção", "Produtos químicos controlados",
            "Encomendas expressas", "Carga volumosa"
    };

    private static final String[] CIDADES = {
            "São Paulo", "Rio de Janeiro", "Belo Horizonte", "Curitiba",
            "Porto Alegre", "Salvador", "Fortaleza", "Manaus",
            "Recife", "Goiânia", "Brasília", "Belém"
    };

    private static final EnumPrioridade[] PRIORIDADES = EnumPrioridade.values();
    private static final EnumPrioridade[] PRIORIDADES_SEM_ZERO = {
            EnumPrioridade.BAIXA,
            EnumPrioridade.MEDIA,
            EnumPrioridade.ALTA,
            EnumPrioridade.CRITICA
    };

    private static final EnumTipo[]       TIPOS         = EnumTipo.values();
    private static final EnumStatus[]     STATUS        = EnumStatus.values();


    private GeradorDados() {
        throw new UnsupportedOperationException("GeradorDados é uma classe utilitária e não deve ser instanciada.");
    }


    public static Atendimento[] gerarAtendimentos(int quantidade) {
        validarQuantidade(quantidade);
        Atendimento[] atendimentos = new Atendimento[quantidade];

        for (int i = 0; i < quantidade; i++) {
            String nome          = nomeAleatorio();
            EnumPrioridade prior = PRIORIDADES[random.nextInt(PRIORIDADES.length)];
            EnumTipo tipo        = TIPOS[random.nextInt(TIPOS.length)];

            atendimentos[i] = new Atendimento(nome, prior, tipo);
        }

        return atendimentos;
    }

    public static Pedido[] gerarPedidos(int quantidade) {
        validarQuantidade(quantidade);
        Pedido[] pedidos = new Pedido[quantidade];

        for (int i = 0; i < quantidade; i++) {
            String nome        = nomeAleatorio();
            String descricao   = DESCRICOES_CARGA[random.nextInt(DESCRICOES_CARGA.length)];
            EnumPrioridade prior = PRIORIDADES[random.nextInt(PRIORIDADES.length)];

            Pedido pedido = new Pedido(nome, descricao, prior);
            pedido.setStatus(STATUS[random.nextInt(STATUS.length)]);
            pedidos[i] = pedido;
        }

        return pedidos;
    }

    public static Entrega[] gerarEntregas(int quantidade) {
        validarQuantidade(quantidade);
        Entrega[] entregas = new Entrega[quantidade];

        Pedido[] pedidos = gerarPedidos(quantidade);
        Rota[]   rotas   = gerarRotas(quantidade);

        for (int i = 0; i < quantidade; i++) {
            EnumPrioridade prior = PRIORIDADES_SEM_ZERO[random.nextInt(PRIORIDADES_SEM_ZERO.length)];
            EnumStatus status    = STATUS[random.nextInt(STATUS.length)];

            Entrega entrega = new Entrega(pedidos[i], rotas[i], prior, status);
            entregas[i] = entrega;
        }

        return entregas;
    }

    public static Rota[] gerarRotas(int quantidade) {
        validarQuantidade(quantidade);
        Rota[] rotas = new Rota[quantidade];

        for (int i = 0; i < quantidade; i++) {
            int origemIdx  = random.nextInt(CIDADES.length);
            int destinoIdx;

            do {
                destinoIdx = random.nextInt(CIDADES.length);
            } while (destinoIdx == origemIdx);

            String origem      = CIDADES[origemIdx];
            String destino     = CIDADES[destinoIdx];
            int distancia      = 1 + random.nextInt(1000);
            String observacoes = "Rota gerada automaticamente";

            Rota rota = new Rota(origem, destino, observacoes);
            rota.setDistanciaEstimadaMt(distancia);
            rotas[i] = rota;
        }

        return rotas;
    }


    public static int[] gerarArrayInteiros(int quantidade) {
        validarQuantidade(quantidade);
        int[] array = new int[quantidade];

        for (int i = 0; i < quantidade; i++) {
            array[i] = random.nextInt(100_000);
        }

        return array;
    }

    public static int[] gerarArrayOrdenado(int quantidade) {
        validarQuantidade(quantidade);
        int[] array = new int[quantidade];

        for (int i = 0; i < quantidade; i++) {
            array[i] = i;
        }

        return array;
    }

    public static int[] gerarArrayInvertido(int quantidade) {
        validarQuantidade(quantidade);
        int[] array = new int[quantidade];

        for (int i = 0; i < quantidade; i++) {
            array[i] = quantidade - 1 - i;
        }

        return array;
    }

    public static int[] gerarArrayQuaseOrdenado(int quantidade) {
        validarQuantidade(quantidade);
        int[] array = gerarArrayOrdenado(quantidade);

        int trocas = Math.max(1, quantidade / 20); // ~5% de mudanças

        for (int i = 0; i < trocas; i++) {
            int a = random.nextInt(quantidade);
            int b = random.nextInt(quantidade);

            int temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        }

        return array;
    }


    private static String nomeAleatorio() {
        return NOMES[random.nextInt(NOMES.length)];
    }

    private static void validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
    }
}