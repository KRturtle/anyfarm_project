package com.example.anyfarm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    //생성자
    public ListViewAdapter() {

    }
    //리스트 아이템의 사이즈
    @Override
    public int getCount() {
        return listViewItemList.size();
    }
    //아이템 가져오기
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }
    //아이템의 포지션값 가져오기
    @Override
    public long getItemId(int position) {
        return position;
    }

    //리스트의 아이템 호출
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_menu_layout, parent, false);
        }

        ImageView icon_img = (ImageView) convertView.findViewById(R.id.list_img);
        TextView title_text = (TextView) convertView.findViewById(R.id.list_text);

        ListViewItem listViewItem = listViewItemList.get(position);

        icon_img.setImageDrawable(listViewItem.getIcon());
        title_text.setText((listViewItem.getTitleStr()));

        return convertView;
    }
    //리스트에 아이디, 제목 추가
    public void addItem(Drawable icon, String title)
    {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitleStr(title);

        listViewItemList.add(item);
    }
}
