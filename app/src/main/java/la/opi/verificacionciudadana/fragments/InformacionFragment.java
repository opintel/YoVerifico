package la.opi.verificacionciudadana.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.model.LatLng;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.EvidencesImagesAdapter;
import la.opi.verificacionciudadana.adapters.PagerAdapterImages;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.database.ActionsDataBase;
import la.opi.verificacionciudadana.models.ImageEvidence;
import la.opi.verificacionciudadana.util.CameraSettings;
import la.opi.verificacionciudadana.util.Comunicater;
import la.opi.verificacionciudadana.util.Config;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 07/02/15.
 */
public class InformacionFragment extends Fragment implements View.OnClickListener, LocationListener {

    public InformacionFragment() {
    }

    public static InformacionFragment newInstance() {
        InformacionFragment aboutFragment = new InformacionFragment();
        Bundle extraArguments = new Bundle();
        aboutFragment.setArguments(extraArguments);
        return aboutFragment;
    }

    ImageView photoEvidence, takePicture;
    TextView titleEvidence;
    final static int REQUEST_IMAGE_CAPTURE = 0;

    String mCurrentPhotoPath;
    String imageFileName;
    int count = 0;

    FloatingActionButton btnCamera;
    GridView gridView;
    PagerAdapterImages pagerAdapterImages;
    Spinner spinner;
    EditText editTxtDescription;
    LocationManager locationManager;
    private Location myLocation;
    String latitude, longitude, accuracy, altitude, calificacion;
    String datePicture;
    JSONArray jsonArrayImg;
    int i = 0;
    String descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_informacion_other, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Capturar Evidencia");

        btnCamera = (FloatingActionButton) rootView.findViewById(R.id.take_pictue);
        editTxtDescription = (EditText) rootView.findViewById(R.id.edit_txt_descripcion);
        spinner = (Spinner) rootView.findViewById(R.id.spinner_evaluation);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        btnCamera.setOnClickListener(this);

        jsonArrayImg = new JSONArray();
        String[] arr = {"En una escala 1 a 5", "1. Malo", "2. Bueno", "3. Regular", "4. Muy bueno", "5. Excelente"};

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.item_spinner_mexico, R.id.item_spinner, arr);

        spinner.setAdapter(arrayAdapter);


        photoEvidence = (ImageView) rootView.findViewById(R.id.image_evidence);


        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapterImages = new PagerAdapterImages(getFragmentManager());

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);

        pager.setAdapter(pagerAdapterImages);


        circlePageIndicator.setViewPager(pager);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     /*   ArrayList<ImageEvidence> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ImageEvidence imageEvidence = new ImageEvidence();
            imageEvidence.setTitle("evidencia " + Integer.toString(i));
            arrayList.add(imageEvidence);
        }

        EvidencesImagesAdapter evidencesImagesAdapter = new EvidencesImagesAdapter(getActivity(),arrayList);

        gridView.setAdapter(evidencesImagesAdapter);*/

        //TODO

        System.out.println(Comunicater.getIdOcurrence());


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                calificacion = evaluacion(position);
                System.out.println(calificacion);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        initializedCamera();

        descripcion = editTxtDescription.getText().toString();


        // myLocation();


    }

    @Override
    public void onResume() {
        super.onResume();

     /*   System.out.println("SE EJECUTO ON RESUME");
        ActionsDataBase.queryDataBase(getActivity());
        ActionsDataBase.getTitleEvidence();
        ActionsDataBase.getPhotoEvidence();

        for (String ss : ActionsDataBase.getTitleEvidence()) {
            System.out.println("se ejecuto el for");
            System.out.println("database values: " + ss);
        }


        ArrayList<ImageEvidence> imageEvidences = new ArrayList<>();

        for (int i = 0; i < ActionsDataBase.getTitleEvidence().size() && i < ActionsDataBase.getPhotoEvidence().size(); i++) {


            ImageEvidence imageEvidence = new ImageEvidence();

            String[] title = new String[ActionsDataBase.getTitleEvidence().size()];
            title = ActionsDataBase.getTitleEvidence().toArray(title);
            String[] photo = new String[ActionsDataBase.getPhotoEvidence().size()];
            photo = ActionsDataBase.getPhotoEvidence().toArray(photo);
            imageEvidence.setTitle(title[i]);
            imageEvidence.setEvidence(photo[i]);

            imageEvidences.add(imageEvidence);
        }

        EvidencesImagesAdapter evidencesImagesAdapter = new EvidencesImagesAdapter(getActivity(), imageEvidences);
        gridView.setAdapter(evidencesImagesAdapter);*/


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == getActivity().RESULT_OK) {


                //todo esto funciona con el image view y ya
              /*  count++;
                setFullImageFromFilePath(mCurrentPhotoPath, photoEvidence);
                photoEvidence.setDrawingCacheEnabled(false);

                titleEvidence.setText("evidencia " + Integer.toString(count));


                if(count == 1){

                    Toast.makeText(getActivity(), "Reuerda que puedes tomar las fotos que desees", Toast.LENGTH_LONG).show();
                }



                Toast.makeText(getActivity(), "Evidencia ha sido guardada", Toast.LENGTH_SHORT).show();*/


                //GUARDAR EN DB
                //  ActionsDataBase.insertDataBase(getActivity(), imageFileName, mCurrentPhotoPath);


                pagerAdapterImages.addFragment(PhotoItem.newInstance(mCurrentPhotoPath, imageFileName));
                pagerAdapterImages.notifyDataSetChanged();

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("timestamp", datePicture);
                    jsonObject.put("format", "PNG");
                    jsonObject.put("name", Integer.toString(i) + imageFileName);

                    i++;

                    jsonArrayImg.put(jsonObject);


                    System.out.println(jsonArrayImg.toString());
                    myLocation();

                    answers();

                } catch (JSONException e) {

                }

            } else if (resultCode == getActivity().RESULT_CANCELED) {
                System.out.println("cancelo result");
            } else {

                System.out.println("cancelo expected");
            }
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

    private void initializedCamera() {
        if (CameraSettings.checkCameraExist(getActivity())) {
            takePictures();
            //answers();
        }
    }

    private void takePictures() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    }

    private File createImageFile() throws IOException {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeData = new SimpleDateFormat(Config.DATA_FORMAT_PICTURE);
        datePicture = timeData.format(c.getTime());
        imageFileName = Config.IMAGE_NAME_DEFOULT + datePicture;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + Config.DIRECTORY_EVIDENCE);
        File image = File.createTempFile(imageFileName, Config.PNG_EXTENSION, storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();


        return image;
    }

    private String evaluacion(int i) {
        String aux = "";

        switch (i) {
            case 1:

                aux = "malo";

                break;

            case 2:
                aux = "bueno";
                break;

            case 3:
                aux = "regular";
                break;
            case 4:
                aux = "muy bueno";

                break;

            case 5:
                aux = "excelente";
                break;


        }
        return aux;
    }

    private void imagenesPath() {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("timestamp", datePicture);
            jsonObject.put("format", "PNG");
            jsonObject.put("name", imageFileName);

            jsonArray.put(jsonObject);


            System.out.println(jsonArray.toString());
        } catch (JSONException e) {

        }


    }

    public void answers() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeData = new SimpleDateFormat(Config.DATA_FORMAT_PICTURE);
        String horaEnvio = timeData.format(c.getTime());

        JSONArray jsonArray = new JSONArray();

        JSONArray verificaciones = new JSONArray();
        JSONArray verificaciones_edit = new JSONArray();
        JSONObject answer = new JSONObject();
        JSONObject actividad = new JSONObject();
        JSONObject locationAtCompletion = new JSONObject();
        JSONObject descriptionObject = new JSONObject();
        JSONObject califacationObject = new JSONObject();
        JSONObject pictureObject = new JSONObject();


        try {


            descriptionObject.put("content", descripcion);
            descriptionObject.put("id", "1");
            descriptionObject.put("updated_at", horaEnvio);
            descriptionObject.put("descripcion", "Escribe alguna observación del evento");
            descriptionObject.put("evidence_type", "com.opi.niteowl.models.Evidence.TextEvidence");


            califacationObject.put("content", calificacion);
            califacationObject.put("id", "2");
            califacationObject.put("descripcion", "bueno");
            califacationObject.put("updated_at", horaEnvio);
            califacationObject.put("evidence_type", "com.opi.niteowl.models.Evidence.MultipleChoiceEvidence");


            pictureObject.put("content", jsonArrayImg);
            pictureObject.put("id", "3");
            pictureObject.put("updated_at", horaEnvio);
            pictureObject.put("evidence_type", "com.opi.niteowl.models.Evidence.PhotoEvidence");

            verificaciones.put(descriptionObject);
            verificaciones.put(califacationObject);
            verificaciones.put(pictureObject);

            locationAtCompletion.put("longitude", longitude);
            locationAtCompletion.put("provider", "gps");
            locationAtCompletion.put("latitude", latitude);
            locationAtCompletion.put("accuracy", accuracy);
            locationAtCompletion.put("altitude", altitude);

            actividad.put("location_at_completion", locationAtCompletion);
            actividad.put("completed_at", "17 Sep 2014 18:05:25 GMT");
            actividad.put("id", Comunicater.getIdOcurrence());


            answer.put("verificaciones", verificaciones);
            answer.put("verificaciones_editadas", verificaciones_edit);
            answer.put("actividad", actividad);


            jsonArray.put(answer);


            System.out.println(jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void myLocation() {

        try {

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                markerLocation();
                answers();
            } else {
                activateGps();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getActivity(), "error inesperado gps", Toast.LENGTH_LONG).show();
        }

    }

    private void activateGps() {
        getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        getActivity().finish();
        Toast.makeText(getActivity(), "GPS ESTA NO ESTA HABILITADO", Toast.LENGTH_LONG).show();
    }

    private void markerLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) 5000, (float) 5.0, this);
        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        System.out.println(myLatLng);

        latitude = String.valueOf(myLocation.getLatitude());
        longitude = String.valueOf(myLocation.getLongitude());
        altitude = String.valueOf(myLocation.getAltitude());
        accuracy = String.valueOf(myLocation.getAccuracy());

        System.out.println(myLocation.getAltitude());
        System.out.println(myLocation.getLatitude());
        System.out.println(myLocation.getLongitude());
        System.out.println(myLocation.getAccuracy());


    }


    private void answersSend(String json, String token, String formToken, String utf) {


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


}
