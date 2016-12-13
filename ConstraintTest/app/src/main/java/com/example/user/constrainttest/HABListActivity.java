package com.example.user.constrainttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by user on 12/12/16.
 */

public class HABListActivity extends Activity {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.emptyTextView) TextView emptyTextView;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<HouseholdAccountBook> list = new ArrayList<HouseholdAccountBook>();
    private HABListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hab_list);
        ButterKnife.bind(this);
        Timber.tag("LiftCycles");
        Timber.d("Activity Created");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getList();
    }

    @OnClick(R.id.backButton) void actionBackButton(Button button) {
        finish();
    }

    @OnClick(R.id.addButton) void actionAddButon(Button button) {
        Intent intent = new Intent(HABListActivity.this, HABDetailActivity.class);
        startActivityForResult(intent, 0);
    }

    private void getList() {
        InterfaceAPI apiService = ServiceGenerator.getClient().create(InterfaceAPI.class);

        Call<ArrayList<HouseholdAccountBook>> call = apiService.getHABList();
        call.enqueue(new Callback<ArrayList<HouseholdAccountBook>>() {
            @Override
            public void onResponse(Call<ArrayList<HouseholdAccountBook>> call, Response<ArrayList<HouseholdAccountBook>> response) {
                list = response.body();

                if (list.size() == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                } else {
                    emptyTextView.setVisibility(View.INVISIBLE);
                }
                adapter = new HABListAdapter(list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ArrayList<HouseholdAccountBook>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
