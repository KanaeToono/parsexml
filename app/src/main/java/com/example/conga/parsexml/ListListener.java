package com.example.conga.parsexml;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by ConGa on 8/03/2016.
 */
public class ListListener implements AdapterView.OnItemClickListener {
    List<StackSite> listItems;
    Activity activity;
    public ListListener(List<StackSite> listItems , Activity activity){
        this.listItems=listItems;
        this.activity=activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(listItems.get(i).getLink()));
        activity.startActivity(intent);

    }
}
