package com.zp.itisme.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.utils.ToastUtils;
import com.zp.itisme.view.CircleImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonSettingActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView iv_icon;
    private View view_selectImage_popup;
    private PopupWindow selectImage_popup;
    private String photoPath;
    private TextView tv_pick;
    private TextView tv_take;
    private TextView tv_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_setting);
    }

    @Override
    protected void initView() {
        iv_icon = (CircleImageView) findViewById(R.id.iv_icon);

        setPopup();
    }

    private void setPopup() {
        view_selectImage_popup = View.inflate(PersonSettingActivity.this, R.layout.view_popup_selectimage, null);

        tv_pick = view_selectImage_popup.findViewById(R.id.tv_pick);
        tv_take = view_selectImage_popup.findViewById(R.id.tv_take);
        tv_cancel = view_selectImage_popup.findViewById(R.id.tv_cancel);

        tv_pick.setOnClickListener(this);
        tv_take.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        selectImage_popup = new PopupWindow(view_selectImage_popup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        selectImage_popup.setBackgroundDrawable(new ColorDrawable());
        selectImage_popup.setOutsideTouchable(false);
        selectImage_popup.setAnimationStyle(R.style.popwin_anim_style);
        selectImage_popup.setFocusable(false);
        selectImage_popup.setTouchable(true);
        selectImage_popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void setListener() {
        iv_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_icon:
                backgroundAlpha(0.4f);
                selectImage_popup.showAtLocation(view_selectImage_popup, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_pick:
                selectImage_popup.dismiss();
                pickImage();
                break;
            case R.id.tv_take:
                selectImage_popup.dismiss();
                takePhoto();
                break;
            case R.id.tv_cancel:
                selectImage_popup.dismiss();
                break;

        }
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED ||
                    cameraPermission != PackageManager.PERMISSION_GRANTED) {
                String[] PERMISSIONS_CAMERA_AND_STORAGE = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(PersonSettingActivity.this, PERMISSIONS_CAMERA_AND_STORAGE, 0x001);
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        photoPath = Environment.getExternalStorageDirectory() + "/Image_" + System.currentTimeMillis() + ".jpg";

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", new File(photoPath));
        } else {
            photoUri = Uri.fromFile(new File(photoPath));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, 0x003);
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED ||
                    cameraPermission != PackageManager.PERMISSION_GRANTED) {
                String[] PERMISSIONS_CAMERA_AND_STORAGE = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(PersonSettingActivity.this, PERMISSIONS_CAMERA_AND_STORAGE, 0x002);
            } else {
                openAlbum();
            }
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 0x004);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x003) {
                try {
                    FileInputStream fis = new FileInputStream(photoPath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    iv_icon.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == 0x004) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    iv_icon.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x001:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    ToastUtils.show(PersonSettingActivity.this, "没有获得权限！");
                }
                break;
            case 0x002:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else {
                    ToastUtils.show(PersonSettingActivity.this, "没有获得权限！");
                }
                break;
        }
    }

}
