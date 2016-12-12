package com.example.user.constrainttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by user on 12/12/16.
 */

public class PhotoListActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        ButterKnife.bind(this);
        Timber.tag("LiftCycles");
        Timber.d("Activity Created");
    }

    @OnClick(R.id.backButton)
    public void actionBackButton() {
        finish();
    }

    @OnClick(R.id.addButton)
    public void actionAddButton() {
        Intent intent = new Intent(PhotoListActivity.this, PhotoDetailActivity.class);
        startActivity(intent);
    }

}
