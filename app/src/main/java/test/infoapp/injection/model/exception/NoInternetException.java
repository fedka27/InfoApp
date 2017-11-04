package test.infoapp.injection.model.exception;

public class NoInternetException extends RuntimeException {
    public static final String ERROR_NO_INTERNET = "There is no internet connection.";

    public NoInternetException() {
        super(ERROR_NO_INTERNET);
    }
}
