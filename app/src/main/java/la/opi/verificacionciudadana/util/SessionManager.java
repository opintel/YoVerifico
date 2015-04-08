package la.opi.verificacionciudadana.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import org.apache.commons.io.IOUtils;
import java.io.StringWriter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.HttpHelper;
import la.opi.verificacionciudadana.tabs.HomeTabs;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 08/04/15.
 */
public class SessionManager {

    public  static void singInRequest(final Context context, String utf, final String tokenLogin, final String userMail, final String userPassword, String rememberme, String commit) {


        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userSingIn(utf, tokenLogin, userMail, userPassword, rememberme, commit)
                .observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);
                    if (HttpHelper.regexLoginSuccess(writer.toString())) {


                        ConfigurationPreferences.setTokenPreference(context, tokenLogin, userPassword, userMail);
                        ConfigurationPreferences.setUserSession(context, "inicio_session_user");





                    } else {


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {


            }
        });


    }


}
