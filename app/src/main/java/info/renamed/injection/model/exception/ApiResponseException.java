package info.renamed.injection.model.exception;

public class ApiResponseException extends RuntimeException {

    public ApiResponseException(String detailMessage) {
        super(detailMessage);
    }

    public ApiResponseException(Throwable throwable) {
        super("Ошибка разбора данных", throwable);
    }

    public ApiResponseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
