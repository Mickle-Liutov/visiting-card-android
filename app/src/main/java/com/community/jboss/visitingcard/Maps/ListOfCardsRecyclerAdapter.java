package com.community.jboss.visitingcard.Maps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.community.jboss.visitingcard.R;

public class ListOfCardsRecyclerAdapter extends RecyclerView.Adapter<ListOfCardsRecyclerAdapter.CardItem> {
    private String[][] mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public ListOfCardsRecyclerAdapter(String[][] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListOfCardsRecyclerAdapter.CardItem onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recycler_item, parent, false);
        CardItem vh = new CardItem(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardItem holder, int position) {
       holder.name.setText(mDataset[position][0]);
       holder.email.setText(mDataset[position][1]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class CardItem extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;
        public CardItem(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }
    }
}
