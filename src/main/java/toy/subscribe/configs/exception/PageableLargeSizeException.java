package toy.subscribe.configs.exception;

public class PageableLargeSizeException extends RuntimeException {
    public PageableLargeSizeException() {
        super("Request page size is too large !");
    }
}
