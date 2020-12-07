package com.example.fitnessdemo.ws;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.example.fitnessdemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private PopupWindow pw;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 600;
    private static int output_Y = 600;

    private ImageView headImage = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ws_fragment_myinfo, container, false);
        findview();
        setListener();
        return root;
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
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
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
                    break;
                case R.id.myinfo_pay:
                    break;
                case R.id.layout_yundong:
                    break;
                case R.id.layout_userinfo:
                    break;
                case R.id.user_shoucang:
                    break;
                case R.id.user_friend:
                    Intent intent = new Intent();
                    intent.setClass(getContext(), FriendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_plan:
                    break;
                case R.id.user_help:
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
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
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

        }
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
