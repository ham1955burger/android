package com.example.user.baseadaptertest.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.baseadaptertest.List.ListAdapter;
import com.example.user.baseadaptertest.List.ListItem;
import com.example.user.baseadaptertest.R;

public class MainActivity extends Activity {

    ListAdapter adapter = new ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView;

        // Adapter 생성
        // adapter = new MainListAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent addActivity = new Intent(MainActivity.this, SubActivity.class);
                addActivity.putExtra("entry", Entry.ADD);
                startActivityForResult(addActivity, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        switch (resultCode) {
            case 0:
                Entry entry = (Entry) data.getExtras().get("entry");
                String image = (String) data.getExtras().get("image");
                String title = (String) data.getExtras().get("title");
                String subTitle = (String) data.getExtras().get("subTitle");

                Integer imageId = 0;

                switch (image) {
                    case "1":
                        imageId = R.drawable.ic_looks_1_black_24dp;
                        break;
                    case "2":
                        imageId = R.drawable.ic_looks_2_black_24dp;
                        break;
                    case "3":
                        imageId = R.drawable.ic_looks_3_black_24dp;
                        break;
                    case "4":
                        imageId = R.drawable.ic_looks_4_black_24dp;
                        break;
                    case "5":
                        imageId = R.drawable.ic_looks_5_black_24dp;
                        break;
                    default:
                        break;
                }

                if (entry == Entry.ADD) {
                    // ADD
                    adapter.addItem(ContextCompat.getDrawable(MainActivity.this, imageId),
                            data.getStringExtra("title"), data.getStringExtra("subTitle"));
                } else {
                    // Edit
                    Integer index = (int) data.getExtras().get("index");
                    ListItem item = (ListItem) adapter.getItem(index);
                    item.setImage(ContextCompat.getDrawable(MainActivity.this, imageId));
                    item.setTitle(title);
                    item.setSubTitle(subTitle);
                }

                adapter.notifyDataSetChanged();

                break;
            default:
                break;
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
            Toast.makeText(getApplicationContext(), String.valueOf(position) + "번째 cell", Toast.LENGTH_SHORT).show();
        }
    };

    private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l_position) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

            final BitmapDrawable imageDrawable = (BitmapDrawable)((ImageView)view.findViewById(R.id.image)).getDrawable();
            final TextView title = (TextView)view.findViewById(R.id.title);
            final TextView subTitle = (TextView)view.findViewById(R.id.subTitle);

            alertDialog.setMessage("수정할래?").setCancelable(false).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

//                    Object itemObj = (Object)adapterView.getAdapter().getItem(position)

                    Intent intent = new Intent(MainActivity.this, SubActivity.class);
                    intent.putExtra("entry", Entry.EDIT);
                    intent.putExtra("index", position);
                    intent.putExtra("image", imageDrawable.getBitmap());
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("subTitle", subTitle.getText().toString());

                    startActivityForResult(intent, 0);
                }
            }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });

            alertDialog.show();
            return true;
        }
    };
}

