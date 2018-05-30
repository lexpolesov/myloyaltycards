package ru.polesov.myloyaltycards.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.R;
import ru.polesov.myloyaltycards.other.ImageUtils;
import ru.polesov.myloyaltycards.presenters.CardPresenter;
import ru.polesov.myloyaltycards.presenters.CardPresenterImpl;


public class CardFragmentImpl extends Fragment implements CardFragment, View.OnClickListener {
    private CardPresenter mCardPresenter;

    private EditText mName;
    public  EditText mCode;
    private EditText mComment;
    private Button mBtnReadCode;
    private Button mBtnFace;
    private Button mBtnBack;
    private Button mBtnSave;
    private ImageView mImageViewFace;
    private ImageView mImageViewBack;
    private static final int REQUEST_DIALOG_BARCODE = 1;
    private static final int REQUEST_PHOTO_FACE= 2;
    private static final int REQUEST_PHOTO_BACK= 3;
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS  = 124;
    private String barcodenumber;
    private boolean barcodeCheck;
    private File mFaceFile;
    private File mBackFile;
    private int colorFace = 0;

    final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    public static CardFragmentImpl newInstance(String id) {
        CardFragmentImpl cartFragment = new CardFragmentImpl();
        Bundle args = new Bundle();
        args.putString("UUID", id);
        cartFragment.setArguments(args);
        return cartFragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_detailed, container, false);
        String id = getArguments().getString("UUID", "");
        if (mCardPresenter == null)
            mCardPresenter = new CardPresenterImpl(this, id);
        mName = (EditText)v.findViewById(R.id.detailed_edit_name);
        mCode = (EditText)v.findViewById(R.id.detailed_edit_number);
        mComment = (EditText)v.findViewById(R.id.detailed_edit_comment);
        mBtnReadCode = (Button)v.findViewById(R.id.detailed_btn_scancode);
        mBtnReadCode.setOnClickListener(this);
        mBtnFace = (Button)v.findViewById(R.id.btn_create_foto1);
        mBtnFace.setOnClickListener(this);
        mBtnBack = (Button)v.findViewById(R.id.btn_create_foto2);
        mBtnBack.setOnClickListener(this);
        mBtnSave = (Button)v.findViewById(R.id.save_card);
        mBtnSave.setOnClickListener(this);
        mImageViewFace = (ImageView)v.findViewById(R.id.imageView_foto1);
        mImageViewBack = (ImageView)v.findViewById(R.id.imageView_foto2);
        mFaceFile = mCardPresenter.getFacePhotoFile();
        mBackFile = mCardPresenter.getBackPhotoFile();
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mFaceFile != null && mBackFile != null &&
                captureImage.resolveActivity(getActivity().getPackageManager()) != null;
        mBtnFace.setEnabled(canTakePhoto);
        mBtnBack.setEnabled(canTakePhoto);
        Card cd = mCardPresenter.getCard();
        colorFace = cd.getColor();
        setName(cd.getTitle());
        setCode(cd.getBarCode());
        setComment(cd.getComment());
        updatePhotoView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (barcodeCheck){
            setCode(barcodenumber);
            barcodeCheck = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailed_btn_scancode:
                mCardPresenter.clickScanCode();
                break;
            case R.id.btn_create_foto1:
                mCardPresenter.clickCreateFoto1();
                break;
            case R.id.btn_create_foto2:
                mCardPresenter.clickCreateFoto2();
                break;
            case R.id.save_card:
                btnSave();
                break;
        }
    }

    private void btnSave(){
        if (mName.getText().length() == 0) {
            Toast.makeText(this.getContext(), "Введите название", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCode.getText().length() == 0) {
            Toast.makeText(this.getContext(), "Введите/Сканируйте код", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((mFaceFile == null || !mFaceFile.exists())) {
            Toast.makeText(this.getContext(), "Сделайте фото лицевой стороны", Toast.LENGTH_SHORT).show();
            return;
        }
        Card card = mCardPresenter.getCard();
        if(colorFace !=0)
            card.setColor(colorFace);
        card.setTitle(mName.getText().toString());
        card.setBarCode(mCode.getText().toString());
        card.setComment(mComment.getText().toString());
        card.setDate(new Date());
        mCardPresenter.clickSave(card);
    }

    @Override
    public void setName(String name) {
       mName.setText(name);
    }

    @Override
    public void setCode(String code) {
       mCode.setText(code);
    }

    @Override
    public void setComment(String comment) {
        mComment.setText(comment);
    }

    @Override
    public void showBarcodeView() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment barcodefragment = new BarcodeFragmentImpl();
            barcodefragment.setTargetFragment(CardFragmentImpl.this, REQUEST_DIALOG_BARCODE);
            fm.beginTransaction().replace(R.id.fragment_container, barcodefragment)
                    .addToBackStack(null)
                    .commit();
        }
        else
            checkPermission(-1);

    }

    @Override
    public void showFotoView(int FaceOrBack) {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Uri uri;
            if (FaceOrBack == 0)
                uri = FileProvider.getUriForFile(getActivity(),
                        "ru.polesov.myloyaltycards.fileprovider",
                        mFaceFile);
            else
                uri = FileProvider.getUriForFile(getActivity(),
                        "ru.polesov.myloyaltycards.fileprovider",
                        mBackFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            List<ResolveInfo> cameraActivities = getActivity()
                    .getPackageManager().queryIntentActivities(captureImage,
                            PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo activity : cameraActivities) {
                getActivity().grantUriPermission(activity.activityInfo.packageName,
                        uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            if (FaceOrBack == 0)
                startActivityForResult(captureImage, REQUEST_PHOTO_FACE);
            else
                startActivityForResult(captureImage, REQUEST_PHOTO_BACK);
        }
        else
            checkPermission(FaceOrBack);
    }

    @Override
    public void finish() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode){
            case REQUEST_DIALOG_BARCODE:
                if(data != null) {
                   final Barcode barcode = data.getParcelableExtra(BarcodeFragmentImpl.EXTRA_BARCODE);
                   barcodeCheck = true;
                   barcodenumber = barcode.displayValue;
                }
                break;
            case REQUEST_PHOTO_FACE:
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "ru.polesov.myloyaltycards.fileprovider",
                        mFaceFile);
                getActivity().revokeUriPermission(uri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Bitmap bitmap = ImageUtils.getScaledBitmap(
                        mFaceFile.getPath(), getActivity());
                if (bitmap != null && !bitmap.isRecycled()) {
                    Palette palette = Palette.from(bitmap).generate();
                    colorFace = palette.getDominantColor(0);
                }
                updatePhotoView();
                break;
            case REQUEST_PHOTO_BACK:

                Uri uri1 = FileProvider.getUriForFile(getActivity(),
                        "ru.polesov.myloyaltycards.fileprovider",
                        mBackFile);
                getActivity().revokeUriPermission(uri1,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                updatePhotoView();
                break;
        }
    }
    private void updatePhotoView(){
        if (mFaceFile == null || !mFaceFile.exists()) {
            mImageViewFace.setImageResource(R.drawable.ic_menu_camera);
        } else {
            Bitmap bitmap = ImageUtils.getScaledBitmap(
                    mFaceFile.getPath(), getActivity());
            mImageViewFace.setImageBitmap(bitmap);
        }
        if (mBackFile == null || !mBackFile.exists()) {
            mImageViewBack.setImageResource(R.drawable.ic_menu_camera);
        } else {
            Bitmap bitmap = ImageUtils.getScaledBitmap(
                    mBackFile.getPath(), getActivity());
            mImageViewBack.setImageBitmap(bitmap);
        }
    }

    private void checkPermission(final int action) {
        List<String> permissionsNeeded = new ArrayList<String>();
        mCardPresenter.setFotoAction(action);
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add(getResources().getString(R.string.permission_camera));
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = getResources().getString(R.string.permission_need) + " " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            else{
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_error_camera), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults ) {
        boolean isCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                if(isCamera){
                int action = mCardPresenter.getFotoAction();
                    switch (action) {
                        case -1:
                            showBarcodeView();
                            break;
                        case 0:
                            showFotoView(0);
                            break;
                        case 1:
                            showFotoView(1);
                            break;
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.toast_error_camera), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
