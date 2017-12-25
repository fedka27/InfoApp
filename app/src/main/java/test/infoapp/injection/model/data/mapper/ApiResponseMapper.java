package test.infoapp.injection.model.data.mapper;

import retrofit2.adapter.rxjava2.Result;
import test.infoapp.injection.model.exception.ApiResponseException;
import test.infoapp.injection.model.exception.NoInternetException;
import test.infoapp.util.connection.ConnectionUtilAbs;


public class ApiResponseMapper {
    private ConnectionUtilAbs connectionUtilAbs;

    public ApiResponseMapper(ConnectionUtilAbs connectionUtilAbs) {
        this.connectionUtilAbs = connectionUtilAbs;
    }

    public <R> R map(Result<R> responseResult) {
        if (responseResult.isError()) {
            // No internet, etc.
            handleInternetException(responseResult.error());
        }
        return responseResult.response().body();
    }

    private void handleInternetException(Throwable throwable) {
        if (!connectionUtilAbs.isOnline()) {
            throw new NoInternetException();
        } else {
            throw new ApiResponseException(throwable);
        }
    }


}
