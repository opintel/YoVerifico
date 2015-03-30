package la.opi.verificacionciudadana.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.StringWriter;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.api.PitagorasMultimediaService;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.database.ActionsDataBase;
import la.opi.verificacionciudadana.models.ImageEvidence;
import la.opi.verificacionciudadana.util.BitmapTransform;
import la.opi.verificacionciudadana.util.Comunicater;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.DataConfig;
import la.opi.verificacionciudadana.util.LocationStatus;
import la.opi.verificacionciudadana.util.StorageFiles;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 10/02/15.
 */
public class EvidenceFourFragment extends Fragment implements View.OnClickListener, LocationListener {

    public EvidenceFourFragment() {
    }

    public static EvidenceFourFragment newInstance() {

        EvidenceFourFragment fragmentEventConfirmation = new EvidenceFourFragment();
        Bundle extraArguments = new Bundle();
        fragmentEventConfirmation.setArguments(extraArguments);
        return fragmentEventConfirmation;
    }

    private Button sendEvidences;
    private TextView txtComments, txtEvaluation, txtPhotos, txtDate;
    private FrameLayout frameLayout;
    private CircleImageView circleImageView;
    private Location myLocation;
    private LocationManager locationManager;
    private String ratingEvaluation;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_confirmation, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.evidence_four_fragment));
        uiWidgets(rootView);
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setValuesUI();
    }


    @Override
    public void onClick(View v) {


        // Toast.makeText(getActivity(), a + b, Toast.LENGTH_SHORT).show();
       StorageFiles.deleteFilesFromDirectory();
        ActionsDataBase.deleteDataBase(getActivity());




        //uploadFiles();
        // este es el que funciona
        //sentJson();
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


    public void sentJson() {

        if (LocationStatus.locationStatus(getActivity())) {

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) 5000, (float) 5.0, this);
            myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            myJson();
            answers(myJson(), EndPoint.PARAMETER_TOKEN, EndPoint.PARAMETER_TOKEN, EndPoint.PARAMETER_UTF8);
            uploadFiles();

        } else {

            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            Toast.makeText(getActivity(), "Es necesario que tu Gps se encuente encendido", Toast.LENGTH_LONG).show();

        }

    }

    public String myJson() {


        JSONArray jsonArray = new JSONArray();
        JSONArray verificaciones = new JSONArray();
        JSONArray verificaciones_edit = new JSONArray();
        JSONObject answer = new JSONObject();
        JSONObject actividad = new JSONObject();
        JSONObject locationAtCompletion = new JSONObject();
        JSONObject descripcionObject = new JSONObject();
        JSONObject califacationObject = new JSONObject();
        JSONObject pictureObject = new JSONObject();


        try {


            pictureObject.put(Config.VERIFICATION_CONTENT_KEY, getArrayPictures());
            pictureObject.put(Config.VERIFICATION_ID_KEY, Config.VERIFICATION_TYPE_VALUE_THREE);
            pictureObject.put(Config.VERIFICATION_UPDATE_KEY, ConfigurationPreferences.getPhotosHourPreference(getActivity()));
            pictureObject.put(Config.VERIFICATION_EVIDENCE_KEY, Config.VERIFICATION_EVIDENCE_VALUE_PHOTOEVIDENCE);
            verificaciones.put(pictureObject);

            califacationObject.put(Config.VERIFICATION_CONTENT_KEY, ratingEvaluation);
            califacationObject.put(Config.VERIFICATION_ID_KEY, Config.VERIFICATION_TYPE_VALUE_TWO);
            califacationObject.put(Config.VERIFICATION_DESCRIPTION_KEY, evaluacion(Integer.parseInt(ratingEvaluation)));
            califacationObject.put(Config.VERIFICATION_UPDATE_KEY, ConfigurationPreferences.getHourCalificationPreference(getActivity()));
            califacationObject.put(Config.VERIFICATION_EVIDENCE_KEY, Config.VERIFICATION_EVIDENCE_VALUE_MULTICHOICE);
            verificaciones.put(califacationObject);

            descripcionObject.put(Config.VERIFICATION_CONTENT_KEY, ConfigurationPreferences.getObservationPreference(getActivity()));
            descripcionObject.put(Config.VERIFICATION_ID_KEY, Config.VERIFICATION_TYPE_VALUE_ONE);
            descripcionObject.put(Config.VERIFICATION_UPDATE_KEY, ConfigurationPreferences.getHourCommentPreference(getActivity()));
            descripcionObject.put(Config.VERIFICATION_DESCRIPTION_KEY, Config.VERIFICATION_DESCRIPTION_VALUE);
            descripcionObject.put(Config.VERIFICATION_EVIDENCE_KEY, Config.VERIFICATION_EVIDENCE_VALUE_TEXT);
            verificaciones.put(descripcionObject);


            locationAtCompletion.put(Config.LOCATION_LONGITUDE_KEY, String.valueOf(myLocation.getLongitude()));
            locationAtCompletion.put(Config.LOCATION_PROVIDER_KEY, Config.LOCATION_PROVIDER_VALUE);
            locationAtCompletion.put(Config.LOCATION_LATITUDE_KEY, String.valueOf(myLocation.getLatitude()));
            locationAtCompletion.put(Config.LOCATION_ACCURACY_KEY, String.valueOf(myLocation.getAccuracy()));
            locationAtCompletion.put(Config.LOCATION_ALTITUDE_KEY, String.valueOf(myLocation.getAltitude()));

            actividad.put(Config.ACTIVITY_LOCATION_AT_KEY, locationAtCompletion);
            actividad.put(Config.ACTIVITY_COMPLETED_AT_KEY, DataConfig.getFechaEnvio());
            actividad.put("id", Comunicater.getIdOcurrence());

            answer.put(Config.VERIFICACIONES_KEY, verificaciones);
            answer.put(Config.VERIFICACIONES_EDIT_KEY, verificaciones_edit);
            answer.put(Config.ACTIVITY_KEY, actividad);


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

    private void pictureReport() {

        ActionsDataBase.queryDataBase(getActivity());

        for (int i = 0; i < ActionsDataBase.getTitleEvidence().size() && i < ActionsDataBase.getPhotoEvidence().size(); i++) {

            String[] photo = new String[ActionsDataBase.getPhotoEvidence().size()];
            photo = ActionsDataBase.getPhotoEvidence().toArray(photo);

            if (i == 0) {

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

    private JSONArray getArrayPictures() {


        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < ActionsDataBase.getTitleEvidence().size(); i++) {

            try {
                String[] titles = new String[ActionsDataBase.getTitleEvidence().size()];
                titles = ActionsDataBase.getTitleEvidence().toArray(titles);

                JSONObject pictureName = new JSONObject();
                pictureName.put(Config.ARRAY_PICTURES_TIMESTAMP, ConfigurationPreferences.getPhotosHourPreference(getActivity()));
                pictureName.put(Config.ARRAY_PICTURES_FORMAT, Config.ARRAY_PICTURES_EXTENSION);
                pictureName.put(Config.ARRAY_PICTURES_NAME,titles[i]);
                jsonArray.put(pictureName);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return jsonArray;
    }

    private void uiWidgets(View rootView) {

        sendEvidences = (Button) rootView.findViewById(R.id.btn_evidence);
        txtComments = (TextView) rootView.findViewById(R.id.txt_observations);
        txtEvaluation = (TextView) rootView.findViewById(R.id.txt_evaluation);
        txtDate = (TextView) rootView.findViewById(R.id.txt_date);
        txtPhotos = (TextView) rootView.findViewById(R.id.txt_photos);
        circleImageView = (CircleImageView) rootView.findViewById(R.id.circleView);
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.container_informacion);
        frameLayout.setBackgroundColor(getResources().getColor(R.color.primary));

    }

    private void setValuesUI() {

        ratingEvaluation = ConfigurationPreferences.getRatingPreference(getActivity());
        String eventComments = ConfigurationPreferences.getObservationPreference(getActivity());
        String photosTotalSize = ConfigurationPreferences.getPhotosSizePreference(getActivity());
        txtEvaluation.setText(ratingEvaluation);
        txtComments.setText(eventComments);
        txtPhotos.setText(photosTotalSize);
        SimpleDateFormat timeData = new SimpleDateFormat(Config.DATA_FORMAT_PICTURE_FECHA);
        txtDate.setText(timeData.format(Calendar.getInstance().getTime()));
        sendEvidences.setOnClickListener(this);
        pictureReport();

    }

    private void uploadFiles() {

        ActionsDataBase.queryDataBase(getActivity());
        ActionsDataBase.getPhotoEvidence();

        PitagorasMultimediaService multimediaService =
                ClientServicePitagoras.getMultimediaRestAdapter().create(PitagorasMultimediaService.class);

        for (int i = 0; i < ActionsDataBase.getPhotoEvidence().size(); i++) {


            String[] photo = new String[ActionsDataBase.getPhotoEvidence().size()];
            photo = ActionsDataBase.getPhotoEvidence().toArray(photo);
            System.out.println("arreglo" + photo[i]);
            final File foto = new File(photo[i]);
            TypedFile file = new TypedFile("png", foto);
            multimediaService.uploadMedia(file).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Response>() {
                        @Override
                        public void call(Response response) {


                            Log.i("response status: ", Integer.toString(response.getStatus()));
                            if (response.getStatus() == 200) {
                                Toast.makeText(getActivity(), "Imagenes alojadas en el S3 vato!", Toast.LENGTH_LONG).show();

                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e(Config.ERROR_RETROFIT, throwable.getMessage());
                        }
                    });

        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
