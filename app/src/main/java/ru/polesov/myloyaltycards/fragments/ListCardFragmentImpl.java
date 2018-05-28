package ru.polesov.myloyaltycards.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.polesov.myloyaltycards.dialogfragment.DialogCardDelete;
import ru.polesov.myloyaltycards.dialogfragment.DialogLongClickMenu;
import ru.polesov.myloyaltycards.RecycleAdapter.AdapterListCard;
import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.R;
import ru.polesov.myloyaltycards.RecycleAdapter.AdapterListener;
import ru.polesov.myloyaltycards.dialogfragment.DialogSortMenu;
import ru.polesov.myloyaltycards.presenters.ListCardPresenterImpl;

public class ListCardFragmentImpl extends Fragment implements ListCardFragment {
    private RecyclerView mListRecyclerView;
    private ListCardPresenterImpl mListCardPresenter;
    private AdapterListCard mAdapter;

    private static final String DIALOG_LONG = "DialogLongClick";
    private static final String DIALOG_DELETE = "DialogDeleteCard";
    private static final String DIALOG_SORT = "DialogSort";
    private static final int REQUEST_DIALOG_LONG = 0;
    private static final int REQUEST_DIALOG_DELETE = 1;
    private static final int REQUEST_DIALOG_SORT = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_cards, container, false);
        mListRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mListCardPresenter == null)
            mListCardPresenter = new ListCardPresenterImpl(this);
        mListCardPresenter.updateUI();
        mListRecyclerView.addOnItemTouchListener(new AdapterListener(getContext(), mListRecyclerView,
                new AdapterListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        AdapterListCard.CardHolder holder = (AdapterListCard.CardHolder) mListRecyclerView
                             .getChildViewHolder(view);
                       mListCardPresenter.OnItemClick(holder.getCard().getID());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        AdapterListCard.CardHolder holder = (AdapterListCard.CardHolder) mListRecyclerView
                                .getChildViewHolder(view);
                        mListCardPresenter.OnLongItemClick(holder.getCard().getID());
                    }
                }));
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListCardPresenter.clickFab();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                mListCardPresenter.clickMenuSorted();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateUI(List<Card> cards) {
        mAdapter = new AdapterListCard(cards);
        mListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showDialogLongClickMenu() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        DialogLongClickMenu dialogLong = new DialogLongClickMenu();
        dialogLong.setTargetFragment(ListCardFragmentImpl.this,REQUEST_DIALOG_LONG);
        dialogLong.show(manager, DIALOG_LONG);
    }

    @Override
    public void showDialogCardDelete() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        DialogCardDelete dialogDelete = new DialogCardDelete();
        dialogDelete.setTargetFragment(ListCardFragmentImpl.this,REQUEST_DIALOG_DELETE);
        dialogDelete.show(manager, DIALOG_DELETE);
    }

    @Override
    public void showCardDetailed(String id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment2 = CardFragmentImpl.newInstance(id);
        fm.beginTransaction().replace(R.id.fragment_container, fragment2)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void showDialogSort(int idsort) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        DialogSortMenu dialogSort = DialogSortMenu.newInstance(idsort);
        dialogSort.setTargetFragment(ListCardFragmentImpl.this,REQUEST_DIALOG_SORT);
        dialogSort.show(manager, DIALOG_SORT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode){
            case REQUEST_DIALOG_LONG:
                int idLongItem = data.getIntExtra(DialogLongClickMenu.EXTRA_WHICH, -1);
                mListCardPresenter.selectedItemLongMenu(idLongItem);
                break;
            case REQUEST_DIALOG_DELETE:
                mListCardPresenter.selectedItemDelete();
                break;
            case REQUEST_DIALOG_SORT:
                mListCardPresenter.selectedMenuSorted(data.getIntExtra(DialogSortMenu.EXTRA_SORT, 0));
                break;
        }
    }

}
