package com.example.fitnessdemo.ws;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    private Uri mUritempFile;

    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();
    //定义Handler对象属性
    private Handler handler;

    private String result;
    private Bitmap bitmap;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 600;
    private static int output_Y = 600;

    private Banner banner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ws_fragment_myinfo, container, false);
        findview();
        setListener();
        initinfo();
        initHandler();
        //设置轮播图
        setBanner();
        return root;
    }

    //设置轮播图
    private void setBanner() {
        banner = root.findViewById(R.id.banner);
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设计图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.ws_banner1);
        list.add(R.drawable.ws_banner2);
        list.add(R.drawable.ws_banner3);
        banner.setImages(list);
        //设置banner的动画效果
        banner.setBannerAnimation(Transformer.ZoomOut);
        //设置轮播时间
        banner.setDelayTime(2500);
        //启动
        banner.start();

    }
    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    private void initHandler() {
        handler = new Handler() {//handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        result = (String) msg.obj;
                        User user = new Gson().fromJson(result, User.class);
                        String str = user.getHeight() + "/" + user.getWeight();
                        String image = user.getImg();

                        String imgUrl = ConfigUtil.SERVER_HOME + image;
                        //使用网络连接下载图片
                        loadimg(imgUrl);
                        tvwh.setText(str);
                        username.setText(user.getName().trim());
                        break;
                    case 2:
                        if (null != ConfigUtil.user_Img){
                            Glide.with(getContext())
                                    .load(ConfigUtil.user_Img)
                                    .circleCrop()
                                    .into(ivtoouxiang);
                        }else {
                            bitmap = (Bitmap) msg.obj;
                            Glide.with(getContext())
                                    .load(bitmap)
                                    .circleCrop()
                                    .into(ivtoouxiang);
                        }
                        break;
                }
            }
        };
    }

    private void loadimg(final String imgUrl) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(imgUrl);
                    InputStream in = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initinfo() {
        User user = new User();
        String str = ConfigUtil.user_Name;
        user.setPhone(str.toString());
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
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.myinfo_setting:
                    intent.setClass(getContext(), TuichuActivity.class);
                    startActivityForResult(intent,3);
                    break;
                case R.id.myinfo_qrcode:
                    break;
                case R.id.myinfo_email:
                    break;
                case R.id.myinfo_touxiang:
                    showEditPhotoWindow(view);
                    break;
                case R.id.myinfo_userinfo:
                    intent.setClass(getContext(), ShentiinfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.myinfo_pay:
                    break;
                case R.id.layout_yundong:
                    break;
                case R.id.layout_userinfo:
                    intent.putExtra("info", result);
                    intent.setClass(getContext(), ShentiinfoActivity.class);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.user_shoucang:
                    break;
                case R.id.user_friend:
                    intent.setClass(getContext(), FriendActivity.class);
                    startActivityForResult(intent, 2);
                    break;
                case R.id.user_plan:
                    break;
                case R.id.user_help:
                    break;
            }
        }
    }

    //显示popwindow
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
//        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
//        //如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
//        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        //如果你想在Activity中得到新打开Activity关闭后返回的数据，
        //你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
//        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);



        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);

    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment//这里的指定位置为sd卡本目录下
                            .getExternalStorageDirectory() + File.separator + "photo.jpeg", IMAGE_FILE_NAME)));
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
//                if (intent != null) {
//                    Uri uri = intent.getData();
//                    if (!TextUtils.isEmpty(uri.getAuthority())) {
//                        Cursor cursor = getContext().getContentResolver().query(uri,
//                                new String[] { MediaStore.Images.Media.DATA },null, null, null);
//                        if (null == cursor) {
//                            Toast.makeText(getActivity(), "图片没找到", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        cursor.moveToFirst();
//                        String  path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                        cursor.close();
//                        Bitmap bitmap = BitmapFactory.decodeFile(path);
//                        ivtoouxiang.setImageBitmap(bitmap);
//                    } else {
//                        String path = uri.getPath();
//                        Bitmap bitmap = BitmapFactory.decodeFile(path);
//                        ivtoouxiang.setImageBitmap(bitmap);
//                    }
//                    } else {
//                    Toast.makeText(getContext(), "图片没找到", Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;
            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getContext(), "没有SDCard!", Toast.LENGTH_LONG).show();
                }

                break;
            case 45:
                System.out.println("                                 456");
                //将Uri图片转换为Bitmap
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(mUritempFile));
//                    ivtoouxiang.setImageBitmap(bitmap);
                    Glide.with(getContext())
                            .load(bitmap)
                            .circleCrop()
                            .into(ivtoouxiang);
                    ConfigUtil.user_Img = bitmap;
//                    if (intent != null) {
//                        Bitmap bitmap1 = intent.getParcelableExtra("data");
//                        //转化格式
//                        String imgString = changeToString(bitmap);
//                        String address = "ImgaddServlet?imgstring=" + imgString;
//                        //上传服务器
//                        serverGetPicture(address);
//                    }
                    //存储到本地
                    //获取本地files目录
//                    String files = getContext().getFilesDir().getAbsolutePath();
//                    String imgs = files + "/imgs";
//                    //判断imgs目录是否存在
//                    File dirImgs = new File(imgs);
//                    if (!dirImgs.exists()) {
//                        //如果目录不存在则创建
//                        dirImgs.mkdir();
//                    }
                    //获取图片的名称
//                    String[] strs = image.split("/");
//                    String imgname = strs[strs.length - 1];
//                    String imgPath = imgs + "/" + imgname;
//                    //获取本地文件输出流
//                    OutputStream out = new FileOutputStream(imgPath);
//                    //循环读写
//                    int b = -1;
//                    while ((b = in1.read()) != -1) {
//                        out.write(b);
//                        out.flush();
//                    }
//                    //断开网络连接
//                    in1.close();
//                    out.close();
//                    //将输入流解析成Bitmap对象
//                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
//
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case 1:
                if (resultCode == 3) {
                    System.out.println("成功跳转");
                }
                break;

            case 2:
                if (resultCode == 6) {
                    System.out.println("成功跳转");
                }
                break;
            case 3:
                if (resultCode == 4) {
                    System.out.println("成功跳转");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * bitmap类型转换
     * @param bitmap
     * @return
     */
    private String changeToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        if(imageString!=null){
            return imageString;
        }
        else {
            return "";
        }

    }

    private void serverGetPicture(String address){
        address = ConfigUtil.SERVER_HOME  + address ;
        Request request = new Request.Builder()
                .url(address)
                .build();
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responsedata=response.body().string();
                if(response!=null){
                    /**
                     * 在安卓中是不允许在子线程中进行UI操作的。但是在主线程直接进行UI操作会造成阻塞，这样app的运行效果就会卡顿。
                     * runOnUiThread()这个方法的作用是将当前线程切换到主线程，所以产生的作用和Handler传递消息的作用是相同的，但是Handler要传递消息再接收消息，不如这个方法简便。
                     */
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }
        });
    }




    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //把裁剪的数据填入里面
        // 设置裁剪
//        intent.putExtra("crop", "true");
//
//        // aspectX , aspectY :宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//        // outputX , outputY : 裁剪图片宽高
//        intent.putExtra("outputX", output_X);
//        intent.putExtra("outputY", output_Y);
//        intent.putExtra("return-data", true);
        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
//        intent.putExtra("return-data", true);      //原本的裁剪方式

        //uritempFile为Uri类变量，实例化uritempFile，转化为uri方式解决问题
        mUritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        System.out.println("                                  123");


        startActivityForResult(intent, 45);

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
        System.out.println("                                          789");
        Uri uri = intent.getData();
        ContentResolver cr = getActivity().getContentResolver();
        Bitmap photo = null;
        try {
            if (uri != null) {
                photo = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } else {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                }
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

}
