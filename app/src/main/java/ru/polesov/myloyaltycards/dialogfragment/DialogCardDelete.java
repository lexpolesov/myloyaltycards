package ru.polesov.myloyaltycards.dialogfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.polesov.myloyaltycards.R;

public class DialogCardDelete extends DialogFragment {

    public static final String EXTRA_DELETE =
            "ru.polesov.myloyaltycards.dialog_delete_card";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.df_delete_name)
                .setPositiveButton(R.string.df_delete_button_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(R.string.df_delete_button_hegative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DELETE, true);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
