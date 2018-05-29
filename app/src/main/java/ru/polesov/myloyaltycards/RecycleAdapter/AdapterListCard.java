package ru.polesov.myloyaltycards.RecycleAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.polesov.myloyaltycards.model.Card;
import ru.polesov.myloyaltycards.R;

public class AdapterListCard extends RecyclerView.Adapter<AdapterListCard.CardHolder> {

    private List<Card> mCards;

    public static class CardHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private Card mCard;
        private CardView mCardView;

        public CardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_card_item, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_card_name);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
        }

        public void setDataCard(Card card){
            mCard = card;
            mTitleTextView.setText(mCard.getTitle());
            mCardView.setCardBackgroundColor(card.getColor());
        }

        public Card getCard() {
            return mCard;
        }
    }
    public AdapterListCard(List<Card> mCards) {
        this.mCards = mCards;
    }
    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CardHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        Card card = mCards.get(position);
        holder.setDataCard(card);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void setCards(List<Card> c) {
        this.mCards = c;
        Log.d("Test", "setCards " + this.mCards.size());
    }
}
