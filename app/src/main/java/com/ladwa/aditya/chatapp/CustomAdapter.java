package com.ladwa.aditya.chatapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AdityaLadwa on 19-01-2015.
 */
public class CustomAdapter extends BaseAdapter {
    Activity activity;
    List<Model> mModel;
    LayoutInflater inflater;


    public CustomAdapter(Activity activity, List<Model> mModel) {
        this.activity = activity;
        this.mModel = mModel;

    }


    @Override
    public int getCount() {
        return mModel.size();

    }

    @Override
    public Object getItem(int position) {

        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Model m = mModel.get(position);


        if (m.isRight() == true)
            convertView = inflater.inflate(R.layout.list_item_message_right, null);
        else
            convertView = inflater.inflate(R.layout.list_item_message_left, null);


        TextView name = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView message = (TextView) convertView.findViewById(R.id.txtMsg);


        name.setText(m.getName());
        message.setText(m.getMessage());

        return convertView;

    }
}
