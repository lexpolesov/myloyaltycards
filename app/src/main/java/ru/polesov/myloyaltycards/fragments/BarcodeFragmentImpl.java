package ru.polesov.myloyaltycards.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import ru.polesov.myloyaltycards.R;

public class BarcodeFragmentImpl extends Fragment {
    public static final String EXTRA_BARCODE =
            "ru.polesov.myloyaltycards.barcode";
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    private boolean findBar = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.barcode, container, false);
        cameraView = (SurfaceView) v.findViewById(R.id.cameraBarcodeView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.EAN_13)
                .build();
        if(!barcode.isOperational()){
            Toast.makeText(getActivity().getApplicationContext(), R.string.error_detector_barcode, Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        cameraSource = new CameraSource.Builder(getContext(), barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        Log.d("Test", "camera barcode");
                        cameraSource.start(cameraView.getHolder());

                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes =  detections.getDetectedItems();
                if(findBar){
                    if(barcodes.size() > 0) {
                        findBar = false;
                        if (getTargetFragment() == null) {
                            return;
                        }
                        exit(barcodes.valueAt(0));
                    }
                }
            }
        });

        Toast.makeText(this.getContext(), R.string.barcode_fragment, Toast.LENGTH_LONG).show();
        Toast.makeText(this.getContext(), R.string.barcode_fragment_focus, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.stop();
    }

    private void exit(Barcode barcode){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_BARCODE, barcode);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
