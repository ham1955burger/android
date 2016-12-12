package com.example.user.constrainttest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by user on 12/12/16.
 */

public class HABDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hab_detail);
        ButterKnife.bind(this);
        Timber.tag("LiftCycles");
        Timber.d("Activity Created");
    }

    @OnClick(R.id.closeButton)
    public void actionCloseButton(Button button) {
        finish();
    }
}
