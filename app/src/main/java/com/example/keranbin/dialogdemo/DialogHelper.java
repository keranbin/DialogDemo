package com.example.keranbin.dialogdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by keranbin on 2016/7/18.
 */
public class DialogHelper {
    /*
    * layout 自定义的dialog样式
    * listViewId 自定义的listView Id
    * backId 自定义的自定义的dialog样式取消键Id
    * titleId 自定义的自定义的dialog样式标题文字Id
    * jsonList 要在listView上显示的数据
    * 获取Json数据中某个属性key
    * */
    public static void createListViewlDialog(Activity context, int layout, int listViewId, int backId, int titleId, final ArrayList<JSONObject> jsonList, String titleName, final DialogListviewListener dialogListviewListener) throws JSONException {

        final Dialog builder = new AlertDialog.Builder(context).create();  //先得到构造器
        final View view = LayoutInflater.from(context).inflate(layout, null);
        ListView listView = (ListView) view.findViewById(listViewId);
        //监听返回键
        ((ImageView) view.findViewById(backId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        //设置dialog的标题
        ((TextView) view.findViewById(titleId)).setText(jsonList.get(0).getString(titleName));

        listView.setAdapter(new SelectNextPeopleAdapter(context, jsonList.subList(1, jsonList.size()), titleName));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //回调所选数据，方便用户进行下一步操作
                dialogListviewListener.setOnlistViewItemClick(jsonList.get(position + 1));
                builder.dismiss();
            }
        });
        builder.show();
        builder.setCancelable(false);//取消底部返回键事件
        builder.getWindow().setContentView(view);
        builder.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /*
    * @context 上下文对象
    * @layout 自定义的dialog样式布局文件
    * @sureId 确定按钮的Id
    * @cancelId 取消按钮的Id
    * @titleId 对话框Textview 的id
    * @title 对话框显示的文本
    * @position 考虑到可能是点击列表的某一项，比如说删除某一项，提示用户是否确定删除，传入下标position，
    *           在回调接口中返回给用户，方便用户获取列表点击项的数据，如果不是这种情况，则可随便传个数值即可，
    * @dialogPromptListener 回调接口
    * */
    public static void createPromptDialog(Activity context, int layout, int sureId, int cancelId, int titleId, String title, final int position, final DialogPromptListener dialogPromptListener) {
        final Dialog builder = new AlertDialog.Builder(context).create();  //先得到构造器
        View view = LayoutInflater.from(context).inflate(layout, null);
        ((TextView) view.findViewById(titleId)).setText(title);
        //监听返回键
        ((Button) view.findViewById(cancelId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        ((Button) view.findViewById(sureId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPromptListener.setOnPrompItemClick(position);
                builder.dismiss();
            }
        });
        builder.show();
        builder.setCancelable(false);//取消底部返回键事件
        builder.getWindow().setContentView(view);
        builder.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public interface DialogListviewListener {
        public void setOnlistViewItemClick(JSONObject jsonObject);
    }

    public interface DialogPromptListener {
        public void setOnPrompItemClick(int position);
    }

}

