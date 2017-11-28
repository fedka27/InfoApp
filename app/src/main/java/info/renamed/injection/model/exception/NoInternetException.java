package info.renamed.injection.model.exception;

public class NoInternetException extends RuntimeException {
    public static final String ERROR_NO_INTERNET = "Нет подключения к интернету";

    public NoInternetException() {
        super(ERROR_NO_INTERNET);
    }
}
