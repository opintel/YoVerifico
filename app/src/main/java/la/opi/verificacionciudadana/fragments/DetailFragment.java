package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.MapaActivity;
import la.opi.verificacionciudadana.dialogs.QuestionDialog;
import la.opi.verificacionciudadana.util.Comunicater;
import la.opi.verificacionciudadana.util.Config;


public class DetailFragment extends Fragment implements View.OnClickListener {

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    public static DetailFragment newInstance() {
        DetailFragment detailFragment = new DetailFragment();
        Bundle extraArguments = new Bundle();
        detailFragment.setArguments(extraArguments);
        return detailFragment;
    }

    private TextView titleEvent, dataEvent, timeEvent, descriptionEvent, placeEvent, contactEvent;
    private FloatingActionButton btnCamera;
    private static final String YOVERIFICO_SHARE_HASHTAG = "\n" + "Nos mueve la paz, Por un México mejor Secretaria de gobernación." +
            "\n" + " enviado desde YoVerifico app.";
    private String titleEventShare, localidad, link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.event_title));
        btnCamera = (FloatingActionButton) rootView.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(this);
        titleEvent = (TextView) rootView.findViewById(R.id.txt_title_event);
        dataEvent = (TextView) rootView.findViewById(R.id.txt_data_detail);
        timeEvent = (TextView) rootView.findViewById(R.id.txt_time_detail);
        descriptionEvent = (TextView) rootView.findViewById(R.id.txt_description_detail);
        placeEvent = (TextView) rootView.findViewById(R.id.txt_place_detail);
        contactEvent = (TextView) rootView.findViewById(R.id.txt_contact_detail);


        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(Config.BUNDLE_OCURRENCE);
            if (bundle != null) {

                titleEvent.setText(bundle.getString(Config.TITLE_OCURRENCE));
                dataEvent.setText(bundle.getString(Config.DATA_OCURRENCE));
                timeEvent.setText(bundle.getString(Config.TIME_OCURRENCE));
                descriptionEvent.setText(bundle.getString(Config.DESCRIPTION_OCURRENCE));
                placeEvent.setText(bundle.getString(Config.PLACE_OCURRENCE));

                titleEventShare = "Evento" + "\n\n" + bundle.getString(Config.TITLE_OCURRENCE);
                localidad = "Lugar" + "\n\n" + bundle.getString(Config.STATE_OCURRRENCE) + " - "
                        + bundle.getString(Config.TWON_OCURRRENCE) + "\n" + bundle.getString(Config.PLACE_OCURRENCE);

                link = "http://maps.google.com/?q=" + bundle.getString(Config.LATITUDE_OCURRENCE) + "," + bundle.getString(Config.LONGITUDE_OCURRENCE);

                Comunicater.setIdOcurrence(bundle.getString(Config.ID_OCURRENCE));
                Comunicater.setTitleOcurrences(bundle.getString(Config.TITLE_OCURRENCE));

                if (bundle.getString(Config.CONTACT_OCURRENCE) == null) {
                    contactEvent.setText(getResources().getString(R.string.info_contact));
                } else {
                    contactEvent.setText(bundle.getString(Config.CONTACT_OCURRENCE));
                }


            }


        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        dialogo();
    }

    private void dialogo() {

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        QuestionDialog dialogo = new QuestionDialog();
        dialogo.show(fragmentManager, "detail_question");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment, menu);

        MenuItem shareItem = menu.findItem(R.id.share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        try {
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareOccurrenceIntent());
            } else {
                Log.d("my tag", "ShareActionProvider is null?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Intent createShareOccurrenceIntent() {
        Intent shareIntent = new Intent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, titleEventShare + "\n\n" + localidad + "\n" + link + "\n" + YOVERIFICO_SHARE_HASHTAG);
        return shareIntent;
    }


}