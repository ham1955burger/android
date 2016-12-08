package com.example.user.tableviewtest.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
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
        adapter.addItem(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_looks_1_black_24dp),
                "기본데이터1", "기본데이터1");
        adapter.addItem(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_looks_2_black_24dp),
                "기본데이터2", "기본데이터2");
        adapter.addItem(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_looks_3_black_24dp),
                "기본데이터3", "기본데이터3");
        adapter.addItem(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_looks_4_black_24dp),
                "기본데이터4", "기본데이터4");
        adapter.addItem(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_looks_5_black_24dp),
                "기본데이터5", "기본데이터5");

        mRecyclerView.setAdapter(adapter);

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

    public void presentAddActivity() {
        Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
        addActivity.putExtra("entry", Entry.ADD);
        startActivityForResult(addActivity, 0);
    }

    public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
        private ArrayList<MainListItem> mainListItems = new ArrayList<MainListItem>();
        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

        public class ViewHolder extends RecyclerView.ViewHolder {
            private FrameLayout rootView;
            private SwipeRevealLayout swipeLayout;
            private View editLayout;
            private View deleteLayout;
            private ImageView image;
            private TextView title;
            private TextView subTitle;

            public ViewHolder(View view) {
                super(view);
                rootView = (FrameLayout) view.findViewById(R.id.root_layout);
                swipeLayout = (SwipeRevealLayout) view.findViewById(R.id.swipe_layout);
                editLayout = view.findViewById(R.id.edit_layout);
                deleteLayout = view.findViewById(R.id.delete_layout);
                image = (ImageView) view.findViewById(R.id.image);
                title = (TextView) view.findViewById(R.id.title);
                subTitle = (TextView) view.findViewById(R.id.subTitle);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MainListAdapter(ArrayList<MainListItem> mainListItems) {
            // uncomment the line below if you want to open only one row at a time
            this.mainListItems = mainListItems;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            // ...
            final ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MainListAdapter.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.image.setImageDrawable(mainListItems.get(position).getImage());
            holder.title.setText(mainListItems.get(position).getTitle());
            holder.subTitle.setText(mainListItems.get(position).getSubTitle());

            viewBinderHelper.bind(holder.swipeLayout, String.valueOf(position));
            viewBinderHelper.setOpenOnlyOne(true);

            holder.getAdapterPosition();

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.swipeLayout.isOpened()) {
                        holder.swipeLayout.close(true);
                    }

                }
            });

            holder.editLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                    final BitmapDrawable imageDrawable = (BitmapDrawable)((ImageView)holder.image).getDrawable();
                    final TextView title = (TextView)holder.title;
                    final TextView subTitle = (TextView)holder.subTitle;

                    alertDialog.setMessage("수정할래?").setCancelable(false).setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

//                    Object itemObj = (Object)adapterView.getAdapter().getItem(position)

                            Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
                            addActivity.putExtra("entry", Entry.EDIT);
                            addActivity.putExtra("index", holder.getAdapterPosition());
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
                    holder.swipeLayout.close(true);
                    alertDialog.show();
                }
            });

            holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                    alertDialog.setMessage("삭제?").setCancelable(false).setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mainListItems.remove(holder.getAdapterPosition());
                            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                    }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    holder.swipeLayout.close(true);
                    alertDialog.show();
                }
            });
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
