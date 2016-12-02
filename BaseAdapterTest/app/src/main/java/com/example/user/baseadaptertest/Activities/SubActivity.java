package com.example.user.baseadaptertest.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.baseadaptertest.R;

/**
 * Created by user on 11/29/16.
 */

enum Entry {
    ADD,
    EDIT
}

public class SubActivity extends Activity {

    Entry entry = Entry.ADD;
    RadioGroup radioGroup;
    EditText editTextTitle;
    EditText editTextSubTitle;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        this.entry = ((Entry) intent.getExtras().get("entry"));

        // radioGroup
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);

        // radioButton
        RadioButton radioButton1 = (RadioButton)findViewById(R.id.radio_button1);
        /*
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.radio_button2);
        RadioButton radioButton3 = (RadioButton)findViewById(R.id.radio_button3);
        RadioButton radioButton4 = (RadioButton)findViewById(R.id.radio_button4);
        RadioButton radioButton5 = (RadioButton)findViewById(R.id.radio_button5);

        radioButton1.setOnClickListener(radioClickListener);
        radioButton2.setOnClickListener(radioClickListener);
        radioButton3.setOnClickListener(radioClickListener);
        radioButton4.setOnClickListener(radioClickListener);
        radioButton5.setOnClickListener(radioClickListener);*/

        radioButton1.setChecked(true);

        // editText
        editTextTitle = (EditText)findViewById(R.id.edit_title);
        editTextSubTitle = (EditText)findViewById(R.id.edit_subTitle);

        // doneButton
        Button doneButton = (Button)findViewById(R.id.done_button);
        doneButton.setOnClickListener(doneButtonClickListener);

        TextView textView = (TextView)findViewById(R.id.navi_title);


        if (this.entry == Entry.EDIT) {
            Bitmap imageBitmap = (Bitmap)intent.getExtras().get("image");
            editTextTitle.setText(intent.getStringExtra("title"));
            editTextSubTitle.setText(intent.getStringExtra("subTitle"));
            index = (int)(intent.getExtras().get("index"));

            Log.d("111AddActivity index : ", String.valueOf(index));
            Log.d("111AddActivity index : ", String.valueOf(index));
            Log.d("111AddActivity index : ", String.valueOf(index));
            Log.d("111AddActivity index : ", String.valueOf(index));

            textView.setText("수정");
            doneButton.setText("수정");
        }
    }

    Button.OnClickListener doneButtonClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (editTextTitle.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "title is not empty!!", Toast.LENGTH_SHORT).show();
                return;
            } else if (editTextSubTitle.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "sub title is not empty!!", Toast.LENGTH_SHORT).show();
                return;
            }

            final RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SubActivity.this);

            String alertStr = "";
            if (entry == Entry.ADD) {
                alertStr = radioButton.getTag().toString() + "번 이미지\n" + editTextTitle.getText().toString() + "\n" + editTextSubTitle.getText().toString() + "\n추가?" ;
            } else {
                alertStr = radioButton.getTag().toString() + "번 이미지\n" + editTextTitle.getText().toString() + "\n" + editTextSubTitle.getText().toString();
            }

            alertDialog.setMessage(alertStr).setCancelable(false).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.putExtra("entry", entry);
                    intent.putExtra("image", radioButton.getTag().toString());
                    intent.putExtra("title", editTextTitle.getText().toString());
                    intent.putExtra("subTitle", editTextSubTitle.getText().toString());

                    if (entry == Entry.EDIT) {
                        intent.putExtra("index", index);
                        Log.d("AddActivity index : ", String.valueOf(index));
                        Log.d("AddActivity index : ", String.valueOf(index));
                        Log.d("AddActivity index : ", String.valueOf(index));
                        Log.d("AddActivity index : ", String.valueOf(index));
                        Log.d("AddActivity index : ", String.valueOf(index));
                        Log.d("AddActivity index : ", String.valueOf(index));
                        Log.d("AddActivity index : ", String.valueOf(index));
                    }
                    setResult(0, intent);
                    finish();
                }
            }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialog.show();
        }
    };

    /*
    RadioButton.OnClickListener radioClickListener = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };*/
}

