package la.opi.verificacionciudadana.api;

import la.opi.verificacionciudadana.util.EndPoint;
import retrofit.client.Response;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Jhordan on 24/02/15.
 */
public interface ApiPitagorasService {

    @GET(EndPoint.MEXICO_STATES)
    Observable<Response> getMexicoStates();


}
