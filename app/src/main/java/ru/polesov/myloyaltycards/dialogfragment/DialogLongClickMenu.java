package ru.polesov.myloyaltycards.dialogfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import ru.polesov.myloyaltycards.R;

public class DialogLongClickMenu extends android.support.v4.app.DialogFragment{
    public static final String EXTRA_WHICH =
            "ru.polesov.myloyaltycards.idlonclickmenuitem";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.df_long_name)
                .setItems(R.array.long_menu_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, which);
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode, int whichMenuItem) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_WHICH, whichMenuItem);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
