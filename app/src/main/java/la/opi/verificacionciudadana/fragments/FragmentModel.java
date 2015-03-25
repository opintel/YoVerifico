package la.opi.verificacionciudadana.fragments;

import android.support.v4.app.Fragment;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 10/02/15.
 */
public abstract class FragmentModel extends Fragment {




    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_informacion, fragmentInstance).addToBackStack(fragmentName)
                .commit();

    }
}
