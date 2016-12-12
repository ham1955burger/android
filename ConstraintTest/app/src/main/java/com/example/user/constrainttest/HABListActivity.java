package com.example.user.constrainttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by user on 12/12/16.
 */

public class HABListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hab_list);
        ButterKnife.bind(this);
        Timber.tag("LiftCycles");
        Timber.d("Activity Created");
    }

    @OnClick(R.id.backButton)
    public void actionBackButton(Button button) {
        finish();
    }

    @OnClick(R.id.addButton)
    public void actionAddButon(Button button) {
        Intent intent = new Intent(HABListActivity.this, HABDetailActivity.class);
        startActivityForResult(intent, 0);
    }
}
