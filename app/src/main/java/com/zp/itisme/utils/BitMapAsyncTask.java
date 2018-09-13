package com.zp.itisme.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class BitMapAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private CallBack cBack;
    private ErrorBack eBack;
    private String imageURL;

    public BitMapAsyncTask(CallBack cBack,ErrorBack eBack) {
        this.cBack=cBack;
        this.eBack = eBack;
    }

    public interface CallBack{
        void senBit(Bitmap bitmap, String url) ;
    }

    public interface ErrorBack{
        void sendError(String result);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        imageURL=params[0];
        return BitmapHttpUtils.getBitMap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result!=null) {
            cBack.senBit(result,imageURL);
        }else {
            eBack.sendError("网络异常，请稍后重试");
        }
    }

}
