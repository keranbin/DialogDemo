package com.example.keranbin.dialogdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class MainActivity extends Activity implements DialogHelper.DialogListviewListener,DialogHelper.DialogPromptListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tvDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showApprovalDialog();
            }
        });

        ((TextView)findViewById(R.id.tvDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog();
            }
        });
    }

    private void showApprovalDialog() {
        try {
            DialogHelper.createListViewlDialog(
                    MainActivity.this,
                    R.layout.dialog_select_approval_people,
                    R.id.lv_select_approval_people,
                    R.id.dialoBack,
                    R.id.titledialog,
                    initData(),
                    "name",
                    this
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<JSONObject> initData() throws JSONException {
        ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
        String strJson = "{\"ryList\":[{\"name\":\"请选择审批人\"},{\"name\":\"张三\"},{\"name\":\"李四\"},{\"name\":\"王五\"},{\"name\":\"王八\"},{\"name\":\"小明\"}]}";
        JSONArray jsonArray = (JSONArray) new JSONTokener(new JSONObject(strJson).getString("ryList")).nextValue();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonList.add(new JSONObject(jsonArray.get(i).toString()));
        }
        return jsonList;
    }

    @Override
    public void setOnlistViewItemClick(JSONObject jsonObject) {
        try {
            Toast.makeText(MainActivity.this, jsonObject.getString("name"), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showDeleteDialog() {
        DialogHelper.createPromptDialog(MainActivity.this,
                R.layout.dialog_delete,
                R.id.btnRight,
                R.id.btnLeft,
                R.id.titledialog,
                "确定删除该条信息?",
                1,
                this
        );
    }

    @Override
    public void setOnPrompItemClick(int position) {
        Toast.makeText(MainActivity.this,"您成功删除了第"+position+"条数据",Toast.LENGTH_LONG).show();
    }
}
