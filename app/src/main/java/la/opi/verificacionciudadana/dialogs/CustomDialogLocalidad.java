package la.opi.verificacionciudadana.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.tabs.HomeTabs;
import la.opi.verificacionciudadana.util.Config;

/**
 * Created by Jhordan on 11/02/15.
 */
public class CustomDialogLocalidad extends DialogFragment {


    public static CustomDialogLocalidad newInstance(int title) {
        CustomDialogLocalidad frag = new CustomDialogLocalidad();
        Bundle args = new Bundle();
        args.putInt(Config.MESSAGE, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt(Config.MESSAGE);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(getResources().getString(title))
                .setPositiveButton(R.string.session_error_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().onBackPressed();
                        dialog.cancel();
                    }
                });


        return builder.create();
    }


}