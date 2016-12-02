package com.example.user.baseadaptertest.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.baseadaptertest.R;

import java.util.ArrayList;

/**
 * Created by user on 11/28/16.
 */

public class ListAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListItem> mainListItem = new ArrayList<ListItem>();

    // MainListAdapter의 생성자
    public ListAdapter() {
    }

    @Override
    public int getCount() {
        return mainListItem.size();
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.image) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.subTitle) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListItem listViewItem = mainListItem.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getImage());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getSubTitle());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return mainListItem.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable image, String title, String subTitle) {
        ListItem item = new ListItem();

        item.setImage(image);
        item.setTitle(title);
        item.setSubTitle(subTitle);

        mainListItem.add(item);
    }
}

