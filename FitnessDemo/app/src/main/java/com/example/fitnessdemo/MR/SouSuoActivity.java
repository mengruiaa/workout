package com.example.fitnessdemo.MR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.History;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SouSuoActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient=new OkHttpClient();
    private List<String> mStrs=new ArrayList<>() ;
    private List<String> hiss=new ArrayList<>() ;
    private History historyOne;
    private ArrayAdapter adapter;
    private ArrayAdapter adapter2;
    private SearchView mSearchView;
    private ListView lListView;
    private ListView history;
    private Gson gson=new Gson();
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://如果服务端返回的数据是字符串
                    System.out.println(mStrs.size());
                    adapter=new ArrayAdapter<String>(SouSuoActivity.this, android.R.layout.simple_list_item_1, mStrs);
                    //获取ImageView的id
                    int imgId = mSearchView.getContext().getResources().getIdentifier("android:id/search_mag_icon",null,null);
//获取ImageView
                    ImageView searchButton = (ImageView)mSearchView.findViewById(imgId);
//设置图片
                    searchButton.setImageResource(R.mipmap.sousuo);
//不使用默认
                    mSearchView.setIconifiedByDefault(false);
//获取提交按钮id
                    int submitId = mSearchView.getContext().getResources().getIdentifier("android:id/search_go_btn",null,null);
                    mSearchView.setSubmitButtonEnabled(true);
                    //拿到提交图标的imageview,
                    ImageView iv_submit = (ImageView) mSearchView.findViewById(submitId);
                    //这样就可以修改图片了
                    //      iv_submit.setImageResource(R.mipmap.sub);

                    //获取到TextView的ID
                    int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
//获取到TextView的控件
                    TextView textView = (TextView) mSearchView.findViewById(id);
                    lListView = (ListView) findViewById(R.id.listView);
                    lListView.setTextFilterEnabled(true);
                    // 设置搜索文本监听
                    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        // 当点击搜索按钮时触发该方法
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            historyOne=new History(ConfigUtil.user_Name,query);
                            addSearchHistory();
                            Intent intent=new Intent(SouSuoActivity.this,CourseDetailActivity.class);
                            intent.putExtra("courseName",query);
                            startActivity(intent);
                            return true;
                        }

                        // 当搜索内容改变时触发该方法
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (!TextUtils.isEmpty(newText)){
                                lListView.setAdapter(adapter);
                                lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TextView textView1=(TextView) view.findViewById(android.R.id.text1);
                                        mSearchView.setQuery(textView1.getText(),true);
                                    }
                                });
                                adapter.getFilter().filter(newText);

                            }else{
                                lListView.setAdapter(null);
                            }
                            return false;
                        }
                    });
                break;
                case 2:
                    adapter2=new ArrayAdapter<String>(SouSuoActivity.this, android.R.layout.simple_list_item_1, hiss);
                    history.setAdapter(adapter2);
                    history.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(getApplicationContext(), CourseDetailActivity.class);
                            intent.putExtra("courseName",hiss.get(position));
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

    private void addSearchHistory() {
        new Thread(){
            @Override
            public void run() {
                //2 创建Request对象
                //1) 使用RequestBody封装请求数据
                //获取待传输数据对应的MIME类型
                MediaType type = MediaType.parse("text/plain");
                //创建RequestBody对象
                RequestBody reqBody = RequestBody.create(type,gson.toJson(historyOne));
                //2) 创建请求对象
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME + "AddHistory")
                        .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 提交请求并获取响应
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.mr_activity_sou_suo);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SouSuoActivity.this.finish();
            }
        });
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setSubmitButtonEnabled(true);
        history=findViewById(R.id.listView_history);
        getAllNames();
        getAllHistory();

    }

    private void getAllHistory() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_HOME + "GetHistory");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设置网络请求的方式为POST
                    conn.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    out.write(ConfigUtil.user_Name.getBytes());
                    out.close();
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取数据
                    String result = reader.readLine();
                    in.close();
                    Type type= new TypeToken<List<String>>(){}.getType();
                    hiss=gson.fromJson(result,type);
                    Message msg = handler.obtainMessage();
                    //设置Message对象的参数
                    msg.what = 2;

                    //发送Message
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getAllNames() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_HOME + "getAllCourNames");
                    //获取网络输入流
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取数据
                    String result = reader.readLine();
                    in.close();
                    Type type= new TypeToken<List<String>>(){}.getType();
                    mStrs=gson.fromJson(result,type);
                    Message msg = handler.obtainMessage();
                    //设置Message对象的参数
                    msg.what = 1;

                    //发送Message
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
