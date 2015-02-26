package la.opi.verificacionciudadana.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;

/**
 * Created by Jhordan on 11/02/15.
 */
public class CustomDialog extends DialogFragment {


    public static CustomDialog newInstance(int title) {
        CustomDialog frag = new CustomDialog();
        Bundle args = new Bundle();
        args.putInt(VerificaCiudadConstants.MESSAGE, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt(VerificaCiudadConstants.MESSAGE);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(getResources().getString(title))
                .setPositiveButton(R.string.session_error_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        return builder.create();
    }


}