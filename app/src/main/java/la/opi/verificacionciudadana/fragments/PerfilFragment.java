package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 07/02/15.
 */
public class PerfilFragment extends Fragment {

    public PerfilFragment() {
    }

    public static PerfilFragment newInstance() {

        PerfilFragment perfilFragment = new PerfilFragment();
        Bundle extraArguments = new Bundle();
        perfilFragment.setArguments(extraArguments);
        return perfilFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // needed to indicate that the fragment would
        // like to add items to the Options Menu
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat/affordance


    }

    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout)
                rootView.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.primary, R.color.accent);
        swipeRefreshLayout.setRefreshing(false);
        return rootView;
    }
}
