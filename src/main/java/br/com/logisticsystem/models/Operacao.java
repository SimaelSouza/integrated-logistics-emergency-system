package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Operacao {

    private final TipoOperacao tipoOperacao;
    private final UUID idEntidade;
    private final Object estadoAnterior;
    private final Object estadoNovo;
    private final LocalDateTime horario;
    private final String descricao;

    public Operacao(TipoOperacao tipoOperacao, UUID idEntidade, Object estadoAnterior,
                    Object estadoNovo, String descricao) {
        validarTipoOperacao(tipoOperacao);
        validarIdEntidade(idEntidade);
        validarDescricao(descricao);

        this.tipoOperacao = tipoOperacao;
        this.idEntidade = idEntidade;
        this.estadoAnterior = estadoAnterior;
        this.estadoNovo = estadoNovo;
        this.horario = LocalDateTime.now();
        this.descricao = descricao;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public UUID getIdEntidade() {
        return idEntidade;
    }

    public Object getEstadoAnterior() {
        return estadoAnterior;
    }

    public Object getEstadoNovo() {
        return estadoNovo;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    private void validarTipoOperacao(TipoOperacao tipoOperacao) {
        if (tipoOperacao == null) {
            throw new IllegalArgumentException("O tipo de operação é obrigatório");
        }
    }

    private void validarIdEntidade(UUID idEntidade) {
        if (idEntidade == null) {
            throw new IllegalArgumentException("O ID da entidade é obrigatório");
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória");
        }
    }

    @Override
    public String toString() {
        return "Operacao{" +
                "tipoOperacao=" + tipoOperacao +
                ", idEntidade=" + idEntidade +
                ", descricao='" + descricao + '\'' +
                ", horario=" + horario +
                '}';
    }
}