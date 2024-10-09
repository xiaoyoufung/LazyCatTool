package se233.lazycattool.exception;

public class ZipExtractionException extends Exception {
    public ZipExtractionException(String message) {
        super(message);
    }

    public ZipExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}