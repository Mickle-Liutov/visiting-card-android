package com.community.jboss.visitingcard.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;

public class ContributersAdapter extends RecyclerView.Adapter<ContributersAdapter.ContribItem> {
    private String[][] mDataset;
    private Context context;

    public ContributersAdapter(String[][] myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContributersAdapter.ContribItem onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contributors_recycler_item, parent, false);
        ContributersAdapter.ContribItem vh = new ContributersAdapter.ContribItem(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContributersAdapter.ContribItem holder, final int position) {
        holder.name.setText(mDataset[position][1]);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(mDataset[position][2]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        if (position == getItemCount() - 1) holder.line.setVisibility(View.INVISIBLE);
        else holder.line.setVisibility(View.VISIBLE);

        Glide.with(context).load(mDataset[position][0]).into(holder.image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.length;
    }

    public static class ContribItem extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public View line;
        public ConstraintLayout container;

        public ContribItem(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.nickname);
            line = v.findViewById(R.id.line);
            container = v.findViewById(R.id.container);
        }
    }
}
