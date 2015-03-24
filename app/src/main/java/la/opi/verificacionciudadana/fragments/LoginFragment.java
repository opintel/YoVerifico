package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.tabs.HomeTabs;

/**
 * Created by Jhordan on 08/03/15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {

        LoginFragment loginFragment = new LoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_login_screen_name);

        ((Button) rootView.findViewById(R.id.btn_session)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.btn_register)).setOnClickListener(this);
        ((ImageView) rootView.findViewById(R.id.pruebas)).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_session:

                fragmentTransactionReplace(SessionFragment.newInstance(), "session_fragment");

                break;

            case R.id.btn_register:

                fragmentTransactionReplace(RegisterFragment.newInstance(), "register_fragment");

                break;

            case R.id.pruebas:
                Intent intent = new Intent(getActivity(), HomeTabs.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }

    }

    private void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentname) {


        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_left)
                .replace(R.id.login_container, fragmentInstance).addToBackStack(fragmentname)
                .commit();

    }


}
