package se233.lazycattool.exception;

public class EmptyImageListException extends RuntimeException {
    public EmptyImageListException() {
        super("The list of images to crop is empty.");
    }

    public EmptyImageListException(String message) {
        super(message);
    }

    public EmptyImageListException(String message, Throwable cause) {
        super(message, cause);
    }
}
