package com.example.fitnessdemo.MR.TheFour;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CourseS extends Fragment {
    private View root;
    private String userName;
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient= new OkHttpClient();
    private Gson gson=new Gson();
    //定义Handler对象属性
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://如果服务端返回的数据是字符串
                    String result = (String) msg.obj;
                    Type type= new TypeToken<List<Course>>(){}.getType();
                    List<Course> cs=gson.fromJson(result,type);
                    Log.i("zlrs", "handleMessage: "+cs.size());
                    break;
                case 2:
                    //获取Message中封装的Bitmap对象
                    Bitmap bitmap = (Bitmap) msg.obj;
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.course, container, false);
        userName= ConfigUtil.user_Name;
//        getReMen();
        new Thread(){
            @Override
            public void run() {
                getZuiXin();
            }
        }.start();
        return root;
    }

    private void getZuiXin() {
        //2 创建Request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(
                type,
                "最新课程"
                );
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "GetZuiXin")
                .post(reqBody)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("mr", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Type type= new TypeToken<List<Course>>(){}.getType();
                List<Course> cs=gson.fromJson(result,type);

                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }

    private void getReMen() {

    }

}
