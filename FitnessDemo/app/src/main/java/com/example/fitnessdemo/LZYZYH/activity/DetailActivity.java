package com.example.fitnessdemo.LZYZYH.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.R;


public class DetailActivity extends AppCompatActivity {
    private TextView tvname;
    private TextView tvtext;
    private TextView tvprice;
    private TextView tvsize;
    private ImageView ivimage;
    private Toast toast;
    private Button addcar;

    private String username;
    private String name;
    private String text;
    private String price;
    private String size;
    private String img;
    private int mipmapId;

//    private Handler myhandler= new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String s = carUIThread(msg);
//            String[] items =  s.split("/")[0].split(",");
//            name = items[0];
//            text = items[1];
//            price = items[2];
//            size = items[3];
//            img = items[4];
//            tvname.setText(name);
//            tvtext.setText(text);
//            tvprice.setText(price);
//            tvsize.setText(size);
//            mipmapId = getResources().getIdentifier(
//                    img,
//                    "mipmap",
//                    getPackageName()
//            );
//            ivimage.setImageResource(mipmapId);
//        }
//    };
    public void initView(){
        tvname = findViewById(R.id.name);
        tvtext = findViewById(R.id.text);
        tvprice = findViewById(R.id.price);
        ivimage = findViewById(R.id.iv_show);
        addcar = findViewById(R.id.addcar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_product_detail);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
//        initView();
//        Intent request = getIntent();
//        String action = request.getAction();
//        String str = request.getStringExtra("str");
//        username = request.getStringExtra("name2");
//        if(!str.equals("0")) {
//            String[] items = str.split(",");
//            name = items[0];
//            text = items[1];
//            price = items[2];
//            size = items[3];
//            img = items[4];
//            tvname.setText(name);
//            tvtext.setText(text);
//            tvprice.setText(price);
//            tvsize.setText(size);
//            mipmapId = getResources().getIdentifier(
//                    img,
//                    "mipmap",
//                    getPackageName()
//            );
//            ivimage.setImageResource(mipmapId);
//        }if("notifMsg".equals(action) && str.equals("0")) {
//            String str2 = request.getStringExtra("extra");
//            Gson gson = new Gson();
//            Product product = gson.fromJson(str2, Product.class);
//            final String productname = "&name="+product.getName();
//            final String C_URL = "http://192.168.137.1:8080/Workout/searchProduct";
////            new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    String str = HttpConnect.ByPost(name,C_URL);
////                    Bundle bundle = new Bundle();
////                    bundle.putString("cakecar",str);
////                    Message msg = new Message();
////                    msg.setData(bundle);
////                    myhandler.sendMessage(msg);
////                }
////            }).start();
//        }
//        addcar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
////            @Override
////            public void onClick(View v) {
////            new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    String u = "&username="+ username +"&cakename="+ name + "&price=" + price + "&size=" + size + "&text=" + text+"&img="+img;
////                    String C_URL = "http://192.168.249.1:8080/CakeWeb/addcar";
////                    HttpConnect.ByPost(u, C_URL);
////                }
////            }).start();
////            toast = Toast.makeText(DetailActivity.this, "添加成功", Toast.LENGTH_LONG);
////            toast.show();
////            }
//        });
    }

}
