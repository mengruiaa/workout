package com.example.fitnessdemo.CLB;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class TestPhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clb_activity_test_photo);
//        transportPhoto(ConfigUtil.SERVER_HOME+"TestPitcureServlet");

    }

    private void transportPhoto(final String s) {
        new Thread(){
            @Override
            public void run() {
                Resources res = getApplication().getResources();
                Bitmap bitmap= BitmapFactory.decodeResource(res, R.mipmap.clb);
                Drawable drawable = new BitmapDrawable(bitmap);
                //将bitmap转换为字节流
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte[] datas = baos.toByteArray();
                try {

                    URL url1 = new URL(s);
                    //获取URLConnection连接对象
                    HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                    conn1.setRequestMethod("POST");
//                    OutputStream out1 = conn1.getOutputStream();
//                    InputStream in1 = getResources().openRawResource(drawable);
//                    int b = -1;
//                    while( (b=in1.read())!=-1){
//                        out1.write(b);
//                    }
//                    conn1.getInputStream();
//                    in1.close();
//                    out1.close();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();

    }
}
