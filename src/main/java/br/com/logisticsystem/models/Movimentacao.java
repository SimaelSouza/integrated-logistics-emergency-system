package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Movimentacao {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final UUID id;
    private final TipoOperacao tipoOperacao;
    private final String descricao;
    private final Object entidadeEnvolvida;
    private final LocalDateTime horario;

    public Movimentacao(TipoOperacao tipoOperacao, String descricao, Object entidadeEnvolvida) {
        validarTipoOperacao(tipoOperacao);
        validarDescricao(descricao);

        this.id = UUID.randomUUID();
        this.tipoOperacao = tipoOperacao;
        this.descricao = descricao;
        this.entidadeEnvolvida = entidadeEnvolvida;
        this.horario = LocalDateTime.now();
    }

    public Movimentacao(TipoOperacao tipoOperacao, String descricao) {
        this(tipoOperacao, descricao, null);
    }


    public UUID getId() {
        return id;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Object getEntidadeEnvolvida() {
        return entidadeEnvolvida;
    }

    public LocalDateTime getHorario() {
        return horario;
    }


    public String getHorarioFormatado() {
        return horario.format(FORMATTER);
    }

    public String getResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getHorarioFormatado()).append("] ");
        sb.append(tipoOperacao.name());
        sb.append(" — ");
        sb.append(descricao);

        if (entidadeEnvolvida != null) {
            sb.append(" | Entidade: ").append(entidadeEnvolvida);
        }

        return sb.toString();
    }


    private void validarTipoOperacao(TipoOperacao tipoOperacao) {
        if (tipoOperacao == null) {
            throw new IllegalArgumentException("O tipo de operação é obrigatório.");
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }
    }

    @Override
    public String toString() {
        return getResumo();
    }
}
