package com.example.anyfarm;

import android.graphics.drawable.Drawable;
//환경설정 리스트뷰
public class ListViewItem {
    private Drawable icon;
    private String titleStr;
    //아이콘, 메뉴 이름에대한 getter,setter
    public void setIcon(Drawable ic) {
        icon = ic;
    }

    public void setTitleStr(String title) {
        titleStr = title;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getTitleStr() {
        return this.titleStr;
    }
}
