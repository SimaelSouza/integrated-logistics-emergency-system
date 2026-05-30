package br.com.logisticsystem.exceptions;

public class PosicaoInvalidaException extends RuntimeException {
    public PosicaoInvalidaException(String message) {
        super(message);
    }

    public PosicaoInvalidaException(String message, Throwable cause) {
      super(message, cause);
    }
}
