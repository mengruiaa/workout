package com.example.fitnessdemo.ws;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomDialog extends DialogFragment {
    private TextView info;
    private TextView infodanwei;
    private EditText infonum;
    private Button baocun;
    private TextView cancel;
    private String infows;
    private String infodanweiws;

    private String weight;
    private String height;

    //定义Handler对象属性
    private Handler handler;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = getActivity().getLayoutInflater().inflate(R.layout.ws_customdialog,null);
//        initinfo();
        final Dialog dialog = new Dialog(getActivity());
        //去除那条蓝色的线
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        //想要让对话框自身变透明，需要直接创建Dialog对象
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        info = view.findViewById(R.id.wsdialog_info);
        infodanwei = view.findViewById(R.id.wsdialog_infodanwei);
        infonum = view.findViewById(R.id.wsdialog_num);
        cancel  = view.findViewById(R.id.wsdialog_cancel);
        baocun = view.findViewById(R.id.btn_wsdialog);

        info.setText(infows);
        infodanwei.setText(infodanweiws);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("体重".equals(infows)){
                    if (Integer.parseInt(infonum.getText().toString())<30){
                        Toast.makeText(getContext(),"体重过低异常",Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(infonum.getText().toString())>180){
                        Toast.makeText(getContext(),"体重过高异常",Toast.LENGTH_SHORT).show();
                    }else{
                        userinfoweight();
                    }
                }else if("身高".equals(infows)){
                        if (Integer.parseInt(infonum.getText().toString())<100){
                            Toast.makeText(getContext(),"身高过低异常",Toast.LENGTH_SHORT).show();
                        }else if (Integer.parseInt(infonum.getText().toString())>260){
                            Toast.makeText(getContext(),"身高过高异常",Toast.LENGTH_SHORT).show();
                        }else{
                            userinfoheight();
                        }
                }
            }
        });

        return dialog;
    }

    public CustomDialog(String info, String infodanwei) {
        this.infows = info;
        this.infodanweiws = infodanwei;
    }
    private void userinfoweight() {
        //1.将注册信息转gson
        User user = new User();
        user.setPhone(ConfigUtil.user_Name);
        int weight = Integer.parseInt(infonum.getText().toString());
        user.setWeight(weight);
        String json = gson.toJson(user);
        //2.创建request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(json, type);
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "BodyinfoupdateServlet")
                .post(reqBody)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result);
                getDialog().dismiss();

//                Intent intent = new Intent();
//                Message msg = handler.obtainMessage();
//                msg.what = 1;
//                msg.obj = result;
//                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

    private void userinfoheight() {
        //1.将注册信息转gson
        User user = new User();
        user.setPhone(ConfigUtil.user_Name);
        int height = Integer.parseInt(infonum.getText().toString());
        user.setHeight(height);
        String json = gson.toJson(user);
        //2.创建request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(json, type);
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "BodyinfoupdateServlet")
                .post(reqBody)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result);
                getDialog().dismiss();

//                Message msg = handler.obtainMessage();
//                msg.what = 1;
//                msg.obj = result;
//                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }
}
