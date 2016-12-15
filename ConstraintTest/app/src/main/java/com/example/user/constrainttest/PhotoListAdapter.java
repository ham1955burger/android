package com.example.user.constrainttest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 12/15/16.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private ArrayList<PhotoBook> photoListItems = new ArrayList<PhotoBook>();

    public PhotoListAdapter(ArrayList<PhotoBook> photoListItems) {
        this.photoListItems = photoListItems;
    }

    private InterfacePhotoListAdapter interfaceHABListAdapter;

    public void setInterfacePhotoListAdapter(InterfacePhotoListAdapter event) {
        this.interfaceHABListAdapter = event;
    }

    public interface InterfacePhotoListAdapter {
        void clickedItem(PhotoBook photoDetail);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        PhotoListAdapter.ViewHolder holder = new PhotoListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PhotoListAdapter.ViewHolder holder, int position) {
        Picasso.with(PhotoListActivity.context).load(ServiceGenerator.BASE_URL + photoListItems.get(position).getImageThumbFile()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceHABListAdapter.clickedItem(photoListItems.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoListItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView) ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}