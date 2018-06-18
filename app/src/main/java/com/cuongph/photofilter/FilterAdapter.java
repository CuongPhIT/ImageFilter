package com.cuongph.photofilter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.cuongph.photofilter.utils.ThumbnailItem;
import com.cuongph.photofilter.imagefilter.imageprocessors.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    private List<ThumbnailItem> thumbnailFilters;
    private OnClickListener listener;

    public FilterAdapter(List<ThumbnailItem> thumbnailFilters) {
        this.thumbnailFilters = thumbnailFilters;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img_item.setImageBitmap(thumbnailFilters.get(position).image);
    }

    @Override
    public int getItemCount() {
        return thumbnailFilters.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_filter)
        ImageView img_item;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClick(thumbnailFilters.get(getAdapterPosition()).filter);
                    }
                }
            });
        }
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    public interface OnClickListener{
        void onClick(Filter filter);
    }
}
