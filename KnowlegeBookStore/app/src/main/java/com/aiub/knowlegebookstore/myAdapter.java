package com.aiub.knowlegebookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    private  Context context;
    private List<upload> uploadList;

    public myAdapter(Context context, List<upload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_layout,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder Holder, int position) {

        upload Upload = uploadList.get(position);
        String productPrice= Double.toString(Upload.getProduct_price());
        Holder.textView.setText(Upload.getProduct_Name());
        Holder.p_price.setText(productPrice);
        Holder.author.setText(Upload.getAuthor());
        Picasso.with(context)
                .load(Upload.getProduct_Uri())
                .fit()
                .centerCrop()
                .into(Holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textView,p_price,p_qty,author,description;
        ImageView imageView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView7);
            p_price = itemView.findViewById(R.id.textView13);
            author =itemView.findViewById(R.id.textView16);
            imageView=itemView.findViewById(R.id.imageView14);

        }
    }
}
