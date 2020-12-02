package com.example.fitnessdemo.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.mob.MobSDK;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;
//import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernumber;
    private EditText userpwd;
    private Button btnlogin;
    private TextView btntoregister;
    private TextView btntofind;
    private TextView tvpro;
    private String username = null;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();
    //定义Handler对象属性
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_login);
        MobSDK.submitPolicyGrantResult(false, null);
        //初始化SMSSDK
        MobSDK.init(this);
        //初始化Handler
        initHandler();
        findview();
        setListener();
        Intent intent = getIntent();
        String action1 = intent.getAction();
        if ("registeruser".equals(action1)) {
            String num = intent.getStringExtra("userphone");
            String pwd = intent.getStringExtra("userpwd");
            usernumber.setText(num);
            userpwd.setText(pwd + "");
        }
    }

    private void initHandler() {
        handler = new Handler() {//handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        String result = (String) msg.obj;
                        if ("账户登录成功".equals(result)) {
                            ConfigUtil.user_Name = username;
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, ShouYeActivity.class);
                            startActivity(intent);
                        } else if ("新账户登录成功".equals(result)){
                            ConfigUtil.user_Name = username;
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, Info1Activity.class);
                            startActivity(intent);
                        } else if ("该账户有误".equals(result)) {
                            Toast.makeText(LoginActivity.this, "该账户登录信息有误", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    private void setListener() {
        MyListener myListener = new MyListener();
        btnlogin.setOnClickListener(myListener);
        btntoregister.setOnClickListener(myListener);
        btntofind.setOnClickListener(myListener);
        tvpro.setOnClickListener(myListener);
    }

    private void findview() {
        usernumber = findViewById(R.id.edit_loginusernum);
        userpwd = findViewById(R.id.edit_loginuserpwd);
        btnlogin = findViewById(R.id.btn_login);
        btntofind = findViewById(R.id.tv_findpwd);
        btntoregister = findViewById(R.id.tv_registeruser);
        tvpro = findViewById(R.id.tv_loginpro);
    }


    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.btn_login:
                    new Thread(){
                        @Override
                        public void run() {
                            Userlogin();
                        }
                    }.start();
                    break;
                case R.id.tv_findpwd:
                    intent.setClass(LoginActivity.this, CustomerserviceActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_registeruser:
                   // sendCode(getApplicationContext());
                    break;
                case R.id.tv_loginpro:
                    intent.setClass(LoginActivity.this, ProtocolActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void Userlogin() {
        //1.将登录信息转gson
        final User user = new User();
        user.setPhone(usernumber.getText().toString());
        user.setPwd(userpwd.getText().toString().trim());
        System.out.println("用户信息" + user.toString().trim());
        String json = gson.toJson(user);
        //2.创建request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(json, type);
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "UserLoginServlet")
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
                username = user.getPhone();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });

    }

//    public void sendCode(Context context) {
//        RegisterPage page = new RegisterPage();
//        //如果使用我们的ui，没有申请模板编号的情况下需传null
//        page.setTempCode(null);
//        page.setRegisterCallback(new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    // 处理成功的结果
//                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
//                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
//                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
//                    System.out.println(phone);
//                    Log.i("ws", "afterEvent: " + phone);
//                    // TODO 利用国家代码和手机号码进行后续的操作
//                    Intent intent = new Intent();
//                    intent.putExtra("userphone", phone);
//                    intent.setClass(LoginActivity.this, RegisterActivity.class);
//                    startActivity(intent);
//                } else {
//                    // TODO 处理错误的结果
//                }
//            }
//        });
//        page.show(context);
//    }

}
