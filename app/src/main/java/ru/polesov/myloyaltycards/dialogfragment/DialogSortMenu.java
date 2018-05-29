package ru.polesov.myloyaltycards.dialogfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.polesov.myloyaltycards.R;

public class DialogSortMenu extends DialogFragment {

    public static final String EXTRA_SORT =
            "ru.polesov.myloyaltycards.dialog_sort";
    private static final String ARG_ID = "iditem";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int id = getArguments().getInt(ARG_ID, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Сортировка").setSingleChoiceItems(R.array.sort_menu_items, id,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                sendResult(Activity.RESULT_OK, item);
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }

    public static DialogSortMenu newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, id);
        DialogSortMenu fragment = new DialogSortMenu();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, int id) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SORT, id);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
