package com.zp.itisme.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.dialog.LoadingDialog;
import com.zp.itisme.utils.CompressImage;
import com.zp.itisme.utils.Config;
import com.zp.itisme.utils.FileRead;
import com.zp.itisme.utils.SPUtils;
import com.zp.itisme.utils.ToastUtils;
import com.zp.itisme.view.KeyboardLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class AddNewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_cancel;
    private TextView tv_sure;
    private EditText et_detail;
    private KeyboardLayout keyboard;
    private RelativeLayout rl_bottom;

    private ImageView add_pic;
    private ImageView iv_add;
    private ImageView iv_add_close;
    private String photoPath;
    private RelativeLayout rl_iv_add;

    private String userid;
    private String username;
    private String icon_path;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
    }

    @Override
    protected void initView() {
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        et_detail = (EditText) findViewById(R.id.et_detail);
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);

        add_pic = (ImageView) findViewById(R.id.add_pic);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add_close = (ImageView) findViewById(R.id.iv_add_close);
        rl_iv_add = (RelativeLayout) findViewById(R.id.rl_iv_add);

        keyboard = (KeyboardLayout) findViewById(R.id.keyboard);
        et_detail.setFocusable(true);
        et_detail.setFocusableInTouchMode(true);
        et_detail.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        userid = SPUtils.get(AddNewActivity.this, "id", "");
        username = SPUtils.get(AddNewActivity.this, "username", "");
        icon_path = SPUtils.get(AddNewActivity.this, "icon_path", "");
    }


    @Override
    protected void setListener() {
        iv_add_close.setOnClickListener(this);
        add_pic.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        keyboard.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl_bottom.getLayoutParams();
                params.bottomMargin = keyboardHeight;
                rl_bottom.setLayoutParams(params);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                toCancel();
                break;
            case R.id.tv_sure:
                toSubmit();
                break;
            case R.id.add_pic:
                addPic();
                break;
            case R.id.iv_add_close:
                clesrImage();
                break;

        }
    }

    private void clesrImage() {
        photoPath = null;
        rl_iv_add.setVisibility(View.GONE);
    }

    private void addPic() {
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
                ActivityCompat.requestPermissions(AddNewActivity.this, PERMISSIONS_CAMERA_AND_STORAGE, 0x002);
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

    private void toSubmit() {
        loadingDialog = LoadingDialog.create(this);
        loadingDialog.show();
        RequestParams params = new RequestParams(Config.ADDSHARE_PATH);
        params.addBodyParameter("userid", userid);
        params.addBodyParameter("user_icon", icon_path);
        params.addBodyParameter("username", username);
        if (!TextUtils.isEmpty(photoPath)) {
            params.addBodyParameter("filename", photoPath.substring(photoPath.lastIndexOf("/"), photoPath.length()));
            String str = new String(Base64.encode(FileRead.byByte(new File(photoPath)), Base64.NO_WRAP));
            params.addBodyParameter("pic", str);
        }else{
            params.addBodyParameter("filename","");
            params.addBodyParameter("pic", "");
        }
        params.addBodyParameter("time", System.currentTimeMillis() + "");
        params.addBodyParameter("detail", et_detail.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess","onSuccess:"+result);
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");

                    if (code == 0) {
                        AddNewActivity.this.finish();
                        listener.returnRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void toCancel() {
        AddNewActivity.this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x004) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = CompressImage.compressScale(BitmapFactory.decodeStream(cr.openInputStream(uri)));
                    photoPath = Environment.getExternalStorageDirectory() + "/Image_" + System.currentTimeMillis() + ".jpg";
                    File file = new File(photoPath);
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 30, fos);
                    iv_add.setImageBitmap(bitmap);
                    rl_iv_add.setVisibility(View.VISIBLE);
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

            case 0x002:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    ToastUtils.show(AddNewActivity.this, "没有获得权限！");
                }
                break;
        }
    }

    //刷新数据
    private static refreshDataListener listener;

    public interface refreshDataListener {
        void returnRefresh();
    }

    public static void setOnDataRefreshListener(refreshDataListener myListener) {
        listener = myListener;
    }


}
