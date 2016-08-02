package com.example.keranbin.dialogdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by keranbin on 2016/3/28.
 */
public class SelectNextPeopleAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<JSONObject> results;
    private String titleName;

    public SelectNextPeopleAdapter(Context context, List<JSONObject> results,String titleName) {
        inflater = LayoutInflater.from(context);
        this.results = results;
        this.titleName=titleName;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dialog_select_approval_people_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvItem = (TextView) convertView
                    .findViewById(R.id.dialogItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            viewHolder.tvItem.setText(results.get(position).getString(titleName));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvItem;
    }
}
