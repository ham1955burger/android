package com.example.user.constrainttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

enum Entry {
    ADD,
    EDIT
}

public class HABDetailActivity extends Activity {
    private static final String TAG = HABDetailActivity.class.getSimpleName();

    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.receiveRadioButton) RadioButton receiveRadioButton;
    @BindView(R.id.payRadioButton) RadioButton payRadioButton;
    @BindView(R.id.dateEditText) EditText dateEditText;
    @BindView(R.id.priceEditText) EditText priceEditText;
    @BindView(R.id.categoryEditText) EditText categoryEditText;
    @BindView(R.id.memoEditText) EditText memoEditText;
    @BindView(R.id.submitButton) Button addButton;

    Entry entry;
    HouseholdAccountBook info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hab_detail);
        ButterKnife.bind(this);
        Timber.tag("LiftCycles");
        Timber.d("Activity Created");

        Intent intent = getIntent();
        entry = (Entry) intent.getExtras().get("entry");

        if (entry == Entry.EDIT) {
            addButton.setText("EDIT");
            info = (HouseholdAccountBook) intent.getExtras().get("info");
            if (info.getState() == "receive") {
                // receive
                receiveRadioButton.setChecked(true);
                payRadioButton.setChecked(false);
            } else {
                // pay
                receiveRadioButton.setChecked(false);
                payRadioButton.setChecked(true);
            }

            dateEditText.setText(info.getDate());
            priceEditText.setText(String.valueOf(info.getPrice()));
            categoryEditText.setText(info.getCategory());
            memoEditText.setText(info.getMemo());
        }

    }

    @OnClick(R.id.closeButton) void actionClose(Button button) {
        finish();
    }

    @OnClick(R.id.submitButton) void actionSubmit(Button button) {
        if (entry == Entry.ADD) {
            post();
        } else {
            put();
        }
    }

    @OnClick(R.id.removeAllButton) void actionRemoveAll(Button button) {
        dateEditText.setText("");
        priceEditText.setText("");
        categoryEditText.setText("");
        memoEditText.setText("");
    }

    @OnClick(R.id.rootConstraintLayout) void hideKeyboard(ConstraintLayout constraintLayout) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dateEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(priceEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(categoryEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(memoEditText.getWindowToken(), 0);
    }

    private void post() {
        InterfaceAPI apiService = ServiceGenerator.getClient().create(InterfaceAPI.class);

        RadioButton checkedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        Log.d(TAG, checkedRadioButton.getText().toString());
        Log.d(TAG, dateEditText.getText().toString());
        Log.d(TAG, priceEditText.getText().toString());
        Log.d(TAG, categoryEditText.getText().toString());
        Log.d(TAG, memoEditText.getText().toString());


        Call<Void> call = apiService.postHAB(
                checkedRadioButton.getText().toString().toLowerCase(),
                dateEditText.getText().toString(),
                Integer.parseInt(priceEditText.getText().toString()),
                categoryEditText.getText().toString(),
                memoEditText.getText().toString()
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, String.valueOf(response.code()));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private void put() {
        InterfaceAPI apiService = ServiceGenerator.getClient().create(InterfaceAPI.class);

        RadioButton checkedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        Log.d(TAG, checkedRadioButton.getText().toString());
        Log.d(TAG, dateEditText.getText().toString());
        Log.d(TAG, priceEditText.getText().toString());
        Log.d(TAG, categoryEditText.getText().toString());
        Log.d(TAG, memoEditText.getText().toString());


        HABDataSend habDataSend = new HABDataSend();
        habDataSend.setDate("2016-12-13");


        Call<Void> call = apiService.putHAB(info.getPk(), habDataSend);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, String.valueOf(response.code()));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}
