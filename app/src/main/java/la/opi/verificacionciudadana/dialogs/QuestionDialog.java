package la.opi.verificacionciudadana.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.InformacionActivity;

/**
 * Created by Jhordan on 11/02/15.
 */
public class QuestionDialog extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.question)
                .setPositiveButton(R.string.yes_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        evidence();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.no_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                        Toast.makeText(getActivity(), "Gracias por la información tu opinion es importante!", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }


    private void evidence(){

        startActivity(new Intent(getActivity(),InformacionActivity.class));
        getActivity().overridePendingTransition(R.animator.open_next, R.animator.close_main);
    }



}