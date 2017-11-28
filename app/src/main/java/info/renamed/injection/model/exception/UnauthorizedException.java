package info.renamed.injection.model.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String ERROR_UNAUTHORIZED = "Unauthorized session was used.";

    public UnauthorizedException() {
        super(ERROR_UNAUTHORIZED);
    }
}
