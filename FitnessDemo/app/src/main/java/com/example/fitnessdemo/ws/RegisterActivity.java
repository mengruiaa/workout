package com.example.fitnessdemo.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {
    private ImageView imageregister;
    private EditText editnum;
    private EditText editprotocol;
    private EditText editpwd;
    private Button btnregister;
    private RadioButton radioButton;
    private TextView tvapp;
    private TextView tvyan;
    private int judgenum = 0;
    private CountDownTimer countDownTimer;//倒计时
    private static final long DELAY_TIME = 2000L;
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
        setContentView(R.layout.ws_activity_register);
        findview();
        Intent intent = getIntent();
        String str = intent.getStringExtra("userphone").trim();
        editnum.setText(str);
        //初始化Handler
        initHandler();
        //初始化倒计时
//       initcountDownTimer();
        setListener();
    }

    /**
     * 初始化Handler对象
     */
    private void initHandler() {
//        HandlerThread handlerThread =
//                new HandlerThread("MyThread");
//        handlerThread.start();
        handler = new Handler() {//handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        String result = (String) msg.obj;
                        if ("账户注册成功".equals(result)) {
                            Userregister();
                        } else if ("该账户已被注册".equals(result)) {
                            Toast.makeText(RegisterActivity.this, "该账户已被注册", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    private void initcountDownTimer() {
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {  //正在计时，按钮不可点击；
                tvyan.setText(millisUntilFinished / 1000 < 10 ? "重新发送:0" + millisUntilFinished / 1000 + "s" : "重新发送:" + millisUntilFinished / 1000 + "s");
                tvyan.setEnabled(false);
            }

            @Override
            public void onFinish() { //计时完成，按钮状态恢复可点击；
                tvyan.setText("重新获取");
                tvyan.setEnabled(true);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void setListener() {
        Mylistener mylistener = new Mylistener();
        imageregister.setOnClickListener(mylistener);
        btnregister.setOnClickListener(mylistener);
        radioButton.setOnClickListener(mylistener);
        tvapp.setOnClickListener(mylistener);
//        tvyan.setOnClickListener(mylistener);
    }

    private void findview() {
        editnum = findViewById(R.id.edit_registeruser);
//        editprotocol = findViewById(R.id.edit_registervalid);
        editpwd = findViewById(R.id.edit_registerpwd);
        imageregister = findViewById(R.id.iv_register);
        btnregister = findViewById(R.id.btn_register);
        radioButton = findViewById(R.id.rb_message_radio);
        tvapp = findViewById(R.id.tv_registerapp);
//        tvyan = findViewById(R.id.tv_yanzhengma);
    }

    class Mylistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.iv_register:
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_register:
                    new Thread() {
                        @Override
                        public void run() {
                            UsertoGson();
                        }
                    }.start();

                    break;
                case R.id.rb_message_radio:
                    judgenum = 1;
                    break;
                case R.id.tv_registerapp:
                    intent.setClass(RegisterActivity.this, ProtocolActivity.class);
                    intent.setAction("register");
                    startActivity(intent);
                    break;
             /*   case R.id.tv_yanzhengma:
                    countDownTimer.start();
                    break;*/
            }
        }
    }

    private void UsertoGson() {
        //1.将注册信息转gson
        User user = new User();
        user.setPhone(editnum.getText().toString());
        user.setPwd(editpwd.getText().toString().trim());
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
                .url(ConfigUtil.SERVER_HOME + "UserRegisterServlet")
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


    private void Userregister() {
        if (judgenum > 0) {
            Toast.makeText(this,
                    "注册成功，登录跳转中",
                    Toast.LENGTH_SHORT).show();
            //设置延时操作
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enterLoginActivity();
                }
            }, DELAY_TIME);

        } else {
            Toast.makeText(this,
                    "请勾选同意APP协议",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void enterLoginActivity() {
        Intent intent = new Intent();
        intent.setAction("registeruser");
        intent.putExtra("userphone", editnum.getText().toString());
        intent.putExtra("userpwd", editpwd.getText() + "");
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
