package com.example.fitnessdemo.ws;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Friendadapter extends BaseAdapter {
    private Context myContext;
    private List<Usershow> usersShowList=new ArrayList<>();
    private int itemLayoutRes;
    private ImageView imguser;

    //定义Handler对象属性
    private Handler handler;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();


    public Friendadapter (Context myContext, List<Usershow> usersShowList, int itemLayoutRes ) {
        this.myContext = myContext;
        this.usersShowList = usersShowList;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {//获得数据的条数
        if (null != usersShowList){
            return usersShowList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if (null != usersShowList){
            return usersShowList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {//获取每个item的id值
        return position;
    }

    //获取item的视图对象
    public View getView(final int position, View convertView, ViewGroup parent) {
        //convertView每个item的视图对象
        //加载item的布局文件
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(myContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
        }
        //获取item中控件的引用
        imguser = convertView.findViewById(R.id.img_user);
        TextView tvfriend = convertView.findViewById(R.id.tv_friusername);
        TextView sex = convertView.findViewById(R.id.tv_sex);
        TextView age = convertView.findViewById(R.id.tv_ageinfo);
        final Button btnguanzhu = convertView.findViewById(R.id.btn_usershow);
        Glide.with(myContext)
                .load(usersShowList.get(position).getBitmap())
                .circleCrop()
                .into(imguser);
        tvfriend.setText(usersShowList.get(position).getName());
        String sexinfo = usersShowList.get(position).getSex();
        if (sexinfo.equals("boy")){
            sex.setText("帅哥");
        }else if(sexinfo.equals("girl")){
            sex.setText("美女");
        }
        age.setText(usersShowList.get(position).getAge()+"");
        btnguanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judgestatus  = btnguanzhu.getText().toString();
                if("关注".equals(judgestatus)){
                    btnguanzhu.setText("已关注");
                    guanzhuuser(usersShowList.get(position).getPhone().toString());
                }else{
                    btnguanzhu.setText("关注");
                    canceluser(usersShowList.get(position).getPhone().toString());
                }
            }
        });

        imguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(myContext,FriendinfoActivity.class);
                intent.putExtra("name",usersShowList.get(position).getPhone());
                intent.setAction("search");
                myContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private void canceluser(final String str) {
        new Thread(){
            @Override
            public void run() {
                Userfocus userfocus = new Userfocus();
                userfocus.setName(ConfigUtil.user_Name);
                userfocus.setFriendname(str);
                System.out.println("用户信息" + userfocus.toString().trim());
                String json = gson.toJson(userfocus);
                //2.创建request对象
                //1) 使用RequestBody封装请求数据
                //获取待传输数据对应的MIME类型
                MediaType type = MediaType.parse("text/plain");
                //创建RequestBody对象
                RequestBody reqBody = RequestBody.create(json, type);
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME + "UserFoucscancelServlet")
                        .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 异步方式提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.i("ws", "请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //获取服务端返回的数据（假设是字符串）
                        String result = response.body().string();
                        Log.i("ws", result);

                        //通知界面信息改变
//                        Toast.makeText(myContext,"取消关注",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }

    private void guanzhuuser(final String str) {
        new Thread(){
            @Override
            public void run() {
               Userfocus userfocus = new Userfocus();
               userfocus.setName(ConfigUtil.user_Name);
               userfocus.setFriendname(str);
                System.out.println("用户信息" + userfocus.toString().trim());
                String json = gson.toJson(userfocus);
                //2.创建request对象
                //1) 使用RequestBody封装请求数据
                //获取待传输数据对应的MIME类型
                MediaType type = MediaType.parse("text/plain");
                //创建RequestBody对象
                RequestBody reqBody = RequestBody.create(json, type);
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME + "UserFocusServlet")
                        .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 异步方式提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.i("ws", "请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //获取服务端返回的数据（假设是字符串）
                        String result = response.body().string();
                        Log.i("ws", result);

                        //通知界面信息改变
//                        Toast.makeText(myContext,"成功关注",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }
}
