package com.example.user.tableviewtest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.user.tableviewtest.MainListItem;
import com.example.user.tableviewtest.R;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private MainListAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MainListItem> mainListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mainListItems = new ArrayList<MainListItem>();
        adapter = new MainListAdapter(mainListItems);
        mRecyclerView.setAdapter(adapter);

        /*
        mRecyclerView.setOnClickListener(itemClickListener);
        mRecyclerView.setOnLongClickListener(itemLongClickListener);*/

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                presentAddActivity();
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
                    MainListItem item = (MainListItem) adapter.getItem(index);
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

    /*
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

                    Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
                    addActivity.putExtra("entry", Entry.EDIT);
                    addActivity.putExtra("index", position);
                    addActivity.putExtra("image", imageDrawable.getBitmap());
                    addActivity.putExtra("title", title.getText().toString());
                    addActivity.putExtra("subTitle", subTitle.getText().toString());

                    startActivityForResult(addActivity, 0);
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
    */

    public void presentAddActivity() {
        Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
        addActivity.putExtra("entry", Entry.ADD);
        startActivityForResult(addActivity, 0);
    }

    public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
        private ArrayList<MainListItem> mainListItems = new ArrayList<MainListItem>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            private SwipeRevealLayout swipeLayout;
            private View addLayout;
            private View editLayout;
            private View deleteLayout;
            private ImageView image;
            private TextView title;
            private TextView subTitle;

            public ViewHolder(View view) {
                super(view);

                swipeLayout = (SwipeRevealLayout) view.findViewById(R.id.swipe_layout);
                addLayout = view.findViewById(R.id.add_layout);
                editLayout = view.findViewById(R.id.edit_layout);
                deleteLayout = view.findViewById(R.id.delete_layout);
                image = (ImageView) view.findViewById(R.id.image);
                title = (TextView) view.findViewById(R.id.title);
                subTitle = (TextView) view.findViewById(R.id.subTitle);

                addLayout.setOnClickListener(clickListener);
                editLayout.setOnClickListener(clickListener);
                deleteLayout.setOnClickListener(clickListener);
            }
        }

        Button.OnClickListener clickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add_layout:
                        presentAddActivity();
                        break;
                    case R.id.edit_layout:
                        break;
                    case R.id.delete_layout:
                        break;
                    default:
                        break;
                }
            }
        };

        // Provide a suitable constructor (depends on the kind of dataset)
        public MainListAdapter(ArrayList<MainListItem> mainListItems) {
            // uncomment the line below if you want to open only one row at a time
            // viewBinderHelper.setOpenOnlyOne(true);
            this.mainListItems = mainListItems;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            // ...

            return new MainListAdapter.ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MainListAdapter.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.image.setImageDrawable(mainListItems.get(position).getImage());
            holder.title.setText(mainListItems.get(position).getTitle());
            holder.subTitle.setText(mainListItems.get(position).getSubTitle());
        }

        @Override
        public int getItemCount() {
            return mainListItems.size();
        }

        public Object getItem(int position) {
            return mainListItems.get(position);
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem(Drawable image, String title, String subTitle) {
            MainListItem item = new MainListItem();

            item.setImage(image);
            item.setTitle(title);
            item.setSubTitle(subTitle);

            mainListItems.add(item);
        }
    }

}
