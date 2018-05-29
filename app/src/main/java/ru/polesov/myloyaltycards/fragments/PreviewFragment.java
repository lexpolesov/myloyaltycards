package ru.polesov.myloyaltycards.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.connectcode.EAN13;

import org.w3c.dom.Text;

import ru.polesov.myloyaltycards.R;

public class PreviewFragment extends Fragment {
    private TextView tv;
    private Button btn;
    private String barcodeStr;
    private String ID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preview_barcode, container, false);
        barcodeStr = getArguments().getString("barcode", "12345678");
        ID = getArguments().getString("uuid", "");
        tv = (TextView) v.findViewById(R.id.textView_preview);
        btn = (Button)v.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment2 = CardFragmentImpl.newInstance(ID);
                fm.beginTransaction().replace(R.id.fragment_container, fragment2)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return v;
    }

    public static PreviewFragment newInstance(String bar, String uuid) {
        Bundle args = new Bundle();
        args.putString("barcode", bar);
        args.putString("uuid", uuid);
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        EAN13 barcode=new EAN13();
        Typeface tf = null;
        tf = Typeface.createFromAsset(getActivity().getAssets(),
                "barcodefonts/ConnectCodeUPCEAN_S3_Trial.ttf");
        String output="";
        String input=barcodeStr;
        String humanReadableText="";
        output=barcode.encode(input,0); //2nd Param indicates Embedded Human Readable
        humanReadableText=barcode.getHumanText();
        Log.d("Test", "EAN13" +  output + " " + humanReadableText);
        tv.setTypeface(tf);
        tv.setText(output);
        tv.setTextSize(48);

    }
}
