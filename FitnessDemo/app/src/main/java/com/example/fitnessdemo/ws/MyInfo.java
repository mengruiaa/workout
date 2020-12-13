package com.example.fitnessdemo.ws;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
import com.example.fitnessdemo.MR.entity.History;
import com.example.fitnessdemo.R;
import com.example.fitnessdemo.ZFT.HistoryActivity;
import com.example.fitnessdemo.ZFT.UserPlanActivity;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_CANCELED;

public class MyInfo extends Fragment {
    private View root;
    private ImageView ivsetting;
    private ImageView ivqrcode;
    private ImageView ivemail;
    private ImageView ivtoouxiang;
    private TextView username;
    private ImageView ivuserinfo;
    private Button btnpay;
    private RelativeLayout yuedong;
    private RelativeLayout usershengti;
    private TextView shoucang;
    private TextView friends;
    private TextView plans;
    private TextView helps;
    private TextView tvwh;
    private PopupWindow pw;
    //zft
    private TextView tvTime;
    private TextView tvHistory;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();
    //定义Handler对象属性
    private Handler handler;

    private String result ;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 600;
    private static int output_Y = 600;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ws_fragment_myinfo, container, false);
        findview();
        setListener();
//        initinfo();
        initHandler();
        getHistoryTime(ConfigUtil.SERVER_HOME + "GetHistoryTimeServlet" + "?user_phone=" + ConfigUtil.user_Name);
        return root;
    }

    private void getHistoryTime(final String s) {
        new Thread(){
            @Override
            public void run() {
                //使用网络连接，接收服务端发送的字符串
                try {
                    //创建URL对象
                    URL url = new URL(s);
                    //获取URLConnection连接对象
                    URLConnection conn = url.openConnection();
                    //获取网络输入流
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String str = reader.readLine();
                    //关闭流
                    reader.close();
                    in.close();
                    //借助于Message，把收到的字符串显示在页面上
                    //创建Message对象
                    Message msg = new Message();
                    //设置Message对象的参数
                    msg.what = 2;
                    msg.obj = str;
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

    private void initHandler() {
        handler = new Handler() {//handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        result = (String) msg.obj;
                        User user = new Gson().fromJson(result, User.class);
                        String str = user.getHeight()+"/"+user.getWeight();
                        tvwh.setText(str);
                        username.setText(user.getName().trim());
                        break;
                    case 2:
                        String res = (String)msg.obj;
                        int total = Integer.parseInt(res);
                        tvTime.setText(total+"");
                }
            }
        };
    }

    private void initinfo() {
            User user = new User();
            String str = ConfigUtil.user_Name;
            user.setPhone(str.toString() );
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
                    .url(ConfigUtil.SERVER_HOME + "BodyinfoServlet")
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

    private void setListener() {
        MyListener myListener = new MyListener();
        ivuserinfo.setOnClickListener(myListener);
        ivtoouxiang.setOnClickListener(myListener);
        ivemail.setOnClickListener(myListener);
        ivqrcode.setOnClickListener(myListener);
        ivsetting.setOnClickListener(myListener);
        btnpay.setOnClickListener(myListener);
        yuedong.setOnClickListener(myListener);
        shoucang.setOnClickListener(myListener);
        usershengti.setOnClickListener(myListener);
        friends.setOnClickListener(myListener);
        plans.setOnClickListener(myListener);
        helps.setOnClickListener(myListener);
    }

    private void findview() {
        ivsetting = root.findViewById(R.id.myinfo_setting);
        ivqrcode = root.findViewById(R.id.myinfo_qrcode);
        ivemail = root.findViewById(R.id.myinfo_email);
        ivtoouxiang = root.findViewById(R.id.myinfo_touxiang);
        username = root.findViewById(R.id.myinfo_username);
        ivuserinfo = root.findViewById(R.id.myinfo_userinfo);
        btnpay = root.findViewById(R.id.myinfo_pay);
        yuedong = root.findViewById(R.id.layout_yundong);
        usershengti = root.findViewById(R.id.layout_userinfo);
        shoucang = root.findViewById(R.id.user_shoucang);
        friends = root.findViewById(R.id.user_friend);
        plans = root.findViewById(R.id.user_plan);
        helps = root.findViewById(R.id.user_help);
        tvwh = root.findViewById(R.id.tv_weightheight);
        tvTime = root.findViewById(R.id.tv_historyTime);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.myinfo_setting:
                    break;
                case R.id.myinfo_qrcode:
                    break;
                case R.id.myinfo_email:
                    break;
                case R.id.myinfo_touxiang:
                    showEditPhotoWindow(view);
                    break;
                case R.id.myinfo_userinfo:
                    intent.setClass(getContext(),ShentiinfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.myinfo_pay:
                    break;
                case R.id.layout_yundong:
                    break;
                case R.id.layout_userinfo:
                    intent.putExtra("info",result);
                    intent.setClass(getContext(), ShentiinfoActivity.class);
                    startActivityForResult(intent,1);
                    break;
                case R.id.user_shoucang:
                    break;
                case R.id.user_friend:
                    intent.setClass(getContext(), FriendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_plan:
                    intent.setClass(getContext(), UserPlanActivity.class);
                    intent.putExtra("userName",ConfigUtil.user_Name);
                    startActivity(intent);
                    break;
                case R.id.user_help:
                    break;
                case R.id.tv_my_history:
                    intent.setClass(getContext(), HistoryActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }



    private void showEditPhotoWindow(View view) {
        View contentView = getLayoutInflater().inflate(R.layout.ws_layout_popwindow, null);
        pw = new PopupWindow(contentView, getActivity().getWindowManager().getDefaultDisplay().getWidth(), getActivity().getWindowManager().getDefaultDisplay().getHeight(), true);
        //设置popupwindow弹出动画
        pw.setAnimationStyle(R.style.popupwindow_anim_style);
        //设置popupwindow背景
        pw.setBackgroundDrawable(new ColorDrawable());
        pw.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        //处理popupwindow
        popupwindowselectphoto(contentView);

    }

    //初始化控件和控件的点击事件
    private void popupwindowselectphoto(View contentView) {
        TextView tv_select_pic = (TextView) contentView.findViewById(R.id.tv_photo);
        TextView tv_pai_pic = (TextView) contentView.findViewById(R.id.tv_photograph);
        TextView tv_cancl = (TextView) contentView.findViewById(R.id.tv_cancle);
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.dialog_ll);
        tv_select_pic.setOnClickListener(pop);
        tv_pai_pic.setOnClickListener(pop);
        tv_cancl.setOnClickListener(pop);
        layout.setOnClickListener(pop);


    }

    private View.OnClickListener pop = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_photo:
                    Toast.makeText(getContext(), "相册 ", Toast.LENGTH_SHORT).show();
                    choseHeadImageFromGallery();
                    break;
                case R.id.tv_photograph:
                    Toast.makeText(getContext(), "拍照 ", Toast.LENGTH_SHORT).show();
                    choseHeadImageFromCameraCapture();
                    break;

                case R.id.tv_cancle:
                    if (pw != null) {
                        pw.dismiss();
                    }
                    break;
                //点击提示框以外的地方关闭
                case R.id.dialog_ll:
                    if (pw != null) {
                        pw.dismiss();
                    }
                    break;
            }

        }

    };

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
//  方法一
//        Intent intentFromGallery = new Intent();
//        // 设置文件类型
//        intentFromGallery.setType("image/*");//选择图片
//        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);

//   方法二
          Intent intentFromGallery  = new Intent(Intent.ACTION_PICK, null);
        //如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        //如果你想在Activity中得到新打开Activity关闭后返回的数据，
        //你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment//这里的指定位置为sd卡本目录下
                            .getExternalStorageDirectory()+ File.separator + "photo.jpeg", IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {//取消
            Toast.makeText(getContext(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST://如果是来自本地的
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getContext(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;
            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//设置图片框
                }

                break;
                case 1:
                if(resultCode==22){
                    System.out.println("成功跳转");
                }
        }



        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面
        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);


//        UCrop.of(uri, mDestinationUri)
//                .withAspectRatio(1, 1)
//                .withMaxResultSize(512, 512)
//                .withTargetActivity(CropActivity.class)
//                .start(mActivity, this);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Uri uri = intent.getData();
        ContentResolver cr = getActivity().getContentResolver();
        Bitmap photo = null;
        try {
            if (uri != null){
                 photo = BitmapFactory.decodeStream(cr.openInputStream(uri));
            }else {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
            ivtoouxiang.setImageBitmap(photo);
        //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory() + "/Ask");
            nf.mkdir();
        //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            File f = new File(Environment.getExternalStorageDirectory() + "/Ask", "okkk.jpg");

            FileOutputStream out = null;
            try {//打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);

                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

//        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getHistoryTime(ConfigUtil.SERVER_HOME + "GetHistoryTimeServlet" + "?user_phone=" + ConfigUtil.user_Name);
    }

}
