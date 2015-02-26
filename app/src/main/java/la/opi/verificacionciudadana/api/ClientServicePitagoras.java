package la.opi.verificacionciudadana.api;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;
import java.net.CookieManager;
import java.net.CookiePolicy;

import la.opi.verificacionciudadana.util.TokenPreference;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 24/02/15.
 */
public class ClientServicePitagoras {

    private static OkClient serviceClient;
    private static RestAdapter restAdapter;
    private static String html;

    public static RestAdapter simplePitagorasRestAdapter() {


        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(EndPoint.HEADER_USER_AGENT, EndPoint.HEADER_AGENT_NAME);
            }
        };


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EndPoint.PITAGORAS_API)
                .setErrorHandler(new RetrofitErrorHandler())
                .setClient(ClientServicePitagoras.getClient())
                .setRequestInterceptor(requestInterceptor)
                .build();
        return restAdapter;
    }

    public static RestAdapter getRestAdapter() {
        if (restAdapter == null || serviceClient == null) {
            restAdapter = simplePitagorasRestAdapter();
        }
        return restAdapter;
    }

    public static OkClient getClient() {

        if (serviceClient == null) {
            OkHttpClient client = new OkHttpClient();
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            client.setCookieHandler(cookieManager);
            serviceClient = new OkClient(client);
        }

        return serviceClient;
    }




}
