package com.zp.itisme.utils;

import android.os.AsyncTask;

public class ByteAsyncTask extends AsyncTask<String, Void, Integer> {

    private CallBack cBack;
    private String byteURL;
    private ErrorBack eBack;

    public ByteAsyncTask(CallBack cBack, ErrorBack eBack) {
        this.cBack = cBack;
        this.eBack = eBack;
    }

    public interface CallBack {
        void senByte(Integer code, String url);
    }

    public interface ErrorBack {
        void sendError(String result, String url);
    }

    @Override
    protected Integer doInBackground(String... params) {
        byteURL = params[0];
        return ByteHttpUtils.getRes(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != null) {
            cBack.senByte(result, byteURL);
        } else {
            eBack.sendError("网络异常，请稍后重试", byteURL);
        }
    }

}
