package com.tars.moneytracker;

import android.graphics.drawable.Drawable;

public interface RecyclerItemClickInterface {

    void onItemClick(int position);
    void onItemClick(Drawable position, String name);


}
