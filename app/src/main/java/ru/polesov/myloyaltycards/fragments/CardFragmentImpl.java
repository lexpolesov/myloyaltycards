package ru.polesov.myloyaltycards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.R;
import ru.polesov.myloyaltycards.presenters.CardPresenter;
import ru.polesov.myloyaltycards.presenters.CardPresenterImpl;


public class CardFragmentImpl extends Fragment implements CardFragment, View.OnClickListener {
    private CardPresenter mCardPresenter;

    private EditText mName;
    public  EditText mCode;
    private EditText mComment;
    private Button mBtnReadCode;
    private Button mBtnFoto1;
    private Button mBtnFoto2;
    private Button mBtnSave;
    private ImageView mImageViewFoto1;
    private ImageView mImageViewFoto2;

    public static CardFragmentImpl newInstance(String id) {
        CardFragmentImpl cartFragment = new CardFragmentImpl();
        Bundle args = new Bundle();
        args.putString("UUID", id);
        cartFragment.setArguments(args);
        return cartFragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String someString = getArguments().getString("UUID", "");
        setRetainInstance(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_detailed, container, false);
        String id = getArguments().getString("UUID", "");
        if (mCardPresenter == null)
            mCardPresenter = new CardPresenterImpl(this);

        mName = (EditText)v.findViewById(R.id.detailed_edit_name);
        mCode = (EditText)v.findViewById(R.id.detailed_edit_number);
        mComment = (EditText)v.findViewById(R.id.detailed_edit_comment);
        mBtnReadCode = (Button)v.findViewById(R.id.detailed_btn_scancode);
        mBtnReadCode.setOnClickListener(this);
        mBtnFoto1 = (Button)v.findViewById(R.id.btn_create_foto1);
        mBtnFoto1.setOnClickListener(this);
        mBtnFoto2 = (Button)v.findViewById(R.id.btn_create_foto2);
        mBtnFoto2.setOnClickListener(this);
        mBtnSave = (Button)v.findViewById(R.id.save_card);
        mBtnSave.setOnClickListener(this);
        mImageViewFoto1 = (ImageView)v.findViewById(R.id.imageView_foto1);
        mImageViewFoto1.setOnClickListener(this);
        mImageViewFoto2 = (ImageView)v.findViewById(R.id.imageView_foto2);
        mImageViewFoto2.setOnClickListener(this);
        mCardPresenter.setId(id);
        return v;
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
                mCardPresenter.clickSave();
                break;
            case R.id.imageView_foto1:
                mCardPresenter.clickViewFoto1();
                break;
            case R.id.imageView_foto2:
                mCardPresenter.clickViewFoto2();
                break;
        }
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
}
