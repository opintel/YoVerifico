package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 08/03/15.
 */
public class SessionFragment extends Fragment {

    public SessionFragment() {
    }

    public static SessionFragment newInstance() {

        SessionFragment loginFragment = new SessionFragment();
        Bundle extraArguments = new Bundle();
        loginFragment.setArguments(extraArguments);
        return loginFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.sessión_with_email);

        return rootView;
    }
}
