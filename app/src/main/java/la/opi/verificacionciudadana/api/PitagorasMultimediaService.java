package la.opi.verificacionciudadana.api;

/*
 * Created by Jhordan on 24/03/15.
 */

import retrofit.client.Response;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface PitagorasMultimediaService {

    @Multipart
    @POST("/levantamientos/multimedia/nite_owl")
    Observable<Response> uploadMedia(@Part("file") TypedFile file);
}
