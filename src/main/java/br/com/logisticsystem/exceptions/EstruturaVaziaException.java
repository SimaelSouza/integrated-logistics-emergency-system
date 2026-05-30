package br.com.logisticsystem.exceptions;

public class EstruturaVaziaException extends RuntimeException {
    public EstruturaVaziaException(String message) {
        super(message);
    }

    public EstruturaVaziaException(String message, Throwable cause) {
        super(message, cause);
    }
}
