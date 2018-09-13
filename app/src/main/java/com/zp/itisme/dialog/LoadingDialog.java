package com.zp.itisme.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.zp.itisme.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public class LoadingDialog {

    public static Dialog mLoadingDialog;
    public static View mLoadingDialogView;
    public static ImageView imageView;

    public static Dialog create(Context context){
        mLoadingDialog = new Dialog(context, R.style.dialog_tyle);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setCancelable(false);
        mLoadingDialogView = View.inflate(context, R.layout.view_dialog_loading_view, null);
        imageView = mLoadingDialogView.findViewById(R.id.imageView);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
        x.image().bind(imageView, "assets://loading.gif", imageOptions);
        mLoadingDialog.setContentView(mLoadingDialogView);
        return mLoadingDialog;
    }

}
