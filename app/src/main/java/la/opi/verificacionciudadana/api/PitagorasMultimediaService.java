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
    @POST(EndPoint.EVIDENCE_MULTIMEDIA_S3)
    Observable<Response> uploadMedia(@Part(EndPoint.PART_FILE) TypedFile file);
}
