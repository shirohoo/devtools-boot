package io.github.shirohoo.devtools.config.exception;

public class PageableLargeSizeException extends RuntimeException {
    public PageableLargeSizeException() {
        super("Request page size is too large !");
    }
}
