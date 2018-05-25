package ru.polesov.myloyaltycards.sampledata;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.polesov.myloyaltycards.R;

public class ListCardFragment extends Fragment {
    private RecyclerView ListRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_card, container, false);
        ListRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        ListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }
}
