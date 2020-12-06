package com.example.fitnessdemo.LZYZYH.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

/**
 * 搜索
 *
 * @author
 *
 */
public class SearchActivity extends Activity implements SearchView.OnQueryTextListener {
    private ImageView img_souse, img_fanhui;
    private List<ListOfGoods> goodsList;
    private SearchView svSearch;
    private List<ListOfGoods> carList;

    private ListView lvShowAllGoods;
    SearchAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    initItem();
                    break;
            }
        }
    };

    private void initItem() {
        adapter = new SearchAdapter(goodsList, R.layout.mall_listofgoods_listview, this);
        lvShowAllGoods.setAdapter(adapter);
        lvShowAllGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("id", goodsList.get(position).getId());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_search);

        lvShowAllGoods = findViewById(R.id.list_listofgoods);
        svSearch = findViewById(R.id.sv_search);
        //设置该SearchView默认是否自动缩小为图标
        svSearch.setIconifiedByDefault(false);
        //为该SearchView组间设置事件监听器
        svSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        svSearch.setSubmitButtonEnabled(true);
        //设置该SearchView内默认显示的提示文本
        svSearch.setQueryHint("请输入要搜索的信息");

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(SERVER_HOME + "ShowProductsServlet");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer buffer = new StringBuffer();
                    String rowId;
                    while ((rowId = reader.readLine()) != null) {
                        buffer.append(rowId);
                    }
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    Log.e("", buffer.toString());
                    goodsList = new ArrayList<>();
                    carList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int goodsId = jsonObject.getInt("id");
                        String biaoti = jsonObject.getString("name");
                        String price = jsonObject.getString("price");
                        String image = jsonObject.getString("imgId");
                        ListOfGoods goods = new ListOfGoods(goodsId, biaoti, price);
                        URL imgUrl = new URL(SERVER_HOME + "/image/"+image);
                        HttpURLConnection imgConn = (HttpURLConnection) imgUrl.openConnection();
                        InputStream imgInputStream = imgConn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(imgInputStream);
                        goods.setImg(bitmap);
                        goodsList.add(goods);
                        carList.add(goods);
                    }
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

//    private void initView() {
//        // img_souse = (ImageView) findViewById(R.id.img_souse);
//        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
//        //  img_fanhui.setOnClickListener(this);
//        img_souse.setOnClickListener(this);
//    }


        //单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        //实际应用中应该在该方法内执行实际查询
        //此处仅使用Toast显示用户输入的查询内容
        goodsList.clear();
        for(int i = 0;i < carList.size();i++){
            if((carList.get(i).getBiaoti().contains(query)) || (carList.get(i).getJiage().contains(query)) || (carList.get(i).getYuanjia().contains(query))){
                goodsList.add(carList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"" + query,Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Toast.makeText(SearchActivity.this, "textChange" + s, Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(s)) {
            //清除ListView的过滤
            lvShowAllGoods.clearTextFilter();

        } else {
            //使用用户输入的内容对ListView的列表项进行过滤
            lvShowAllGoods.setFilterText(s);
        }
        return true;
    }
//用户输入字符时激发该方法
//    public boolean onQueryTextChange(String newText) {

//    }

//    @Override
//    public void onClick(View arg0) {
//        switch (arg0.getId()) {
//            case R.id.img_souse:
//                Intent intent = new Intent(arg0.getContext(), ListOfGoodsActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.img_fanhui:
//                finish();
//                break;
//        }
//    }

}