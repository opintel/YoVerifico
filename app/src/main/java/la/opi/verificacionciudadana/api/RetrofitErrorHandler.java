package la.opi.verificacionciudadana.api;

import android.util.Log;
import java.net.ConnectException;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;


/**
 * Created by Jhordan on 24/02/15.
 */

public class RetrofitErrorHandler implements ErrorHandler {


    @Override
    public Throwable handleError(RetrofitError cause) {


        Log.e("Error en peticion HTTP: ", cause.getMessage());

        if (cause.getKind() == RetrofitError.Kind.NETWORK)
            return new ConnectException(cause.getMessage());


        return cause;
    }
}
