package com.example.user.constrainttest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 12/13/16.
 */


public class HABListAdapter extends RecyclerView.Adapter<HABListAdapter.ViewHolder> {
    private ArrayList<HouseholdAccountBook> habListItems = new ArrayList<HouseholdAccountBook>();

    /*
    public InterfaceHABListAdapter interfaceHABListAdapter = new InterfaceHABListAdapter() {
        @Override
        public void clickedItem(String id) {

        }
    };
    */

    public interface InterfaceHABListAdapter {
        public void clickedItem(String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hab_list_item, parent, false);
        HABListAdapter.ViewHolder holder = new HABListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HABListAdapter.ViewHolder holder, int position) {
        holder.stateTextView.setText(habListItems.get(position).getState());
        holder.categoryPriceTextView.setText(habListItems.get(position).getCategory() + " / " + habListItems.get(position).getPrice());
        holder.memoTextView.setText(habListItems.get(position).getMemo());
    }

    @Override
    public int getItemCount() {
        return habListItems.size();
    }

    public HABListAdapter(ArrayList<HouseholdAccountBook> habListItems) {
        this.habListItems = habListItems;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stateTextView) TextView stateTextView;
        @BindView(R.id.categoryPriceTextView) TextView categoryPriceTextView;
        @BindView(R.id.memoTextView) TextView memoTextView;

        /*
        private TextView stateTextView;
        private TextView categoryPriceTextView;
        private TextView memoTextView;*/


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            /*
            stateTextView = (TextView) itemView.findViewById(R.id.stateTextView);
            categoryPriceTextView = (TextView) itemView.findViewById(R.id.categoryPriceTextView);
            memoTextView = (TextView) itemView.findViewById(R.id.memoTextView);*/
        }

        @OnClick(R.id.itemLinearLayout) void clickedItem() {
            // TODO: interface 붙여서 callback 구현
        }
    }

    public void addItem(String state, String category, int price, String memo, int pk, String date) {
        HouseholdAccountBook item = new HouseholdAccountBook();

        item.setState(state);
        item.setCategory(category);
        item.setPrice(price);
        item.setMemo(memo);
        item.setPk(pk);
        item.setDate(date);

        habListItems.add(item);
    }
}
