package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;

import de.hdodenhof.circleimageview.CircleImageView;
import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.database.ActionsDataBase;
import la.opi.verificacionciudadana.models.ImageEvidence;
import la.opi.verificacionciudadana.util.BitmapTransform;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 10/02/15.
 */
public class EvidenceFourFragment extends Fragment implements View.OnClickListener {

    public EvidenceFourFragment() {
    }

    public static EvidenceFourFragment newInstance() {

        EvidenceFourFragment fragmentEventConfirmation = new EvidenceFourFragment();
        Bundle extraArguments = new Bundle();
        fragmentEventConfirmation.setArguments(extraArguments);
        return fragmentEventConfirmation;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private Button sendEvidences;
    private TextView txtComments, txtEvaluation, txtPhotos;
    private FrameLayout frameLayout;
    private CircleImageView circleImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_confirmation, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Tu Reporte");

        sendEvidences = (Button) rootView.findViewById(R.id.btn_evidence);
        txtComments = (TextView) rootView.findViewById(R.id.txt_observations);
        txtEvaluation = (TextView) rootView.findViewById(R.id.txt_evaluation);
        txtPhotos = (TextView) rootView.findViewById(R.id.txt_photos);
        circleImageView = (CircleImageView)rootView.findViewById(R.id.circleView);
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.container_informacion);
        frameLayout.setBackgroundColor(getResources().getColor(R.color.primary));


        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String ratingEvaluation = ConfigurationPreferences.getRatingPreference(getActivity());
        String eventComments = ConfigurationPreferences.getObservationPreference(getActivity());
        String photosTotalSize = ConfigurationPreferences.getPhotosSizePreference(getActivity());
        txtEvaluation.setText(ratingEvaluation);
        txtComments.setText(eventComments);
        txtPhotos.setText(photosTotalSize);
        sendEvidences.setOnClickListener(this);
        pictureReport();


    }

    @Override
    public void onClick(View v) {


        //   Toast.makeText(getActivity(), a + b, Toast.LENGTH_SHORT).show();
        // StorageFiles.deleteFilesFromDirectory();


        // answers(myJson(), EndPoint.PARAMETER_TOKEN, EndPoint.PARAMETER_TOKEN, EndPoint.PARAMETER_UTF8);


    }


    private void answers(String json, String token, String formToken, String utf) {


        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.sentAnswers(json, token, formToken, utf)
                .observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                System.out.println("ANSWER 1" + response.getStatus());

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);

                    System.out.println("ANSWER 2" + writer.toString());


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


    public String myJson() {

        JSONArray jsonArray = new JSONArray();

        JSONArray verificaciones = new JSONArray();
        JSONArray verificaciones_edit = new JSONArray();
        JSONObject answer = new JSONObject();
        JSONObject actividad = new JSONObject();
        JSONObject locationAtCompletion = new JSONObject();
        JSONObject ocurrencia = new JSONObject();


        try {

            for (int i = 0; i < 3; i++) {

                ocurrencia.put("content", "esta bueno el evento");
                ocurrencia.put("id", "1");
                ocurrencia.put("updated_at", "17 Sep 2014 18:05:24 GMT");
                ocurrencia.put("task_id", "1");
                ocurrencia.put("descripcion", "");
                ocurrencia.put("evidence_type", "la.opi.verificacionciudadana.Evidence");
                ocurrencia.put("evidence_id", "1");
                ocurrencia.put("valid_flag", "1");
                ocurrencia.put("edit_count", "1");

                verificaciones.put(ocurrencia);

            }


            locationAtCompletion.put("longitude", "-99.16582529");
            locationAtCompletion.put("provider", "gps");
            locationAtCompletion.put("latitude", "19.41554712");
            locationAtCompletion.put("accuracy", "16");
            locationAtCompletion.put("altitude", "2221.39990234375");

            actividad.put("location_at_completion", locationAtCompletion);
            actividad.put("completed_at", "17 Sep 2014 18:05:25 GMT");


            answer.put("verificaciones", verificaciones);
            answer.put("verificaciones_editadas", verificaciones_edit);
            answer.put("actividad", actividad);


            jsonArray.put(answer);


            System.out.println(jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    private String evaluacion(int i) {
        String aux = "";
        switch (i) {
            case 1:
                aux = getString(R.string.malo_ponderation);
                break;
            case 2:
                aux = getString(R.string.bueno_ponderation);
                break;
            case 3:
                aux = getString(R.string.regular_ponderation);
                break;
            case 4:
                aux = getString(R.string.my_bueno_ponderation);
                break;
            case 5:
                aux = getString(R.string.excelente_ponderation);
                break;
        }
        return aux;
    }

    private void pictureReport(){

       ActionsDataBase.queryDataBase(getActivity());

        for (int i = 0; i < ActionsDataBase.getTitleEvidence().size() && i < ActionsDataBase.getPhotoEvidence().size(); i++) {

            String[] photo = new String[ActionsDataBase.getPhotoEvidence().size()];
            photo = ActionsDataBase.getPhotoEvidence().toArray(photo);
            if(i==0){

                int MAX_WIDTH = 1024;
                int MAX_HEIGHT = 768;
                int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
                Picasso.with(getActivity()).load("file://" + photo[0])
                        .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                        .skipMemoryCache()
                        .resize(size, size)
                        .centerInside()
                        .into(circleImageView);
            }

        }

    }


}
