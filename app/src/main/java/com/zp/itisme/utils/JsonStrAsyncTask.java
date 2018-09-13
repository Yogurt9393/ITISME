package com.zp.itisme.utils;

import android.os.AsyncTask;

public class JsonStrAsyncTask extends AsyncTask<String, Void, String> {

    private CallBack cBack;
    private ErrorBack eBack;
    private String strURL;

    public JsonStrAsyncTask(CallBack cBack, ErrorBack eBack) {
        this.cBack = cBack;
        this.eBack = eBack;
    }

    public interface CallBack {
        void senStr(String result, String url);
    }

    public interface ErrorBack {
        void sendError(String result);
    }

    @Override
    protected String doInBackground(String... params) {
        strURL = params[0];
        return JsonHttpUtils.getJSONStr(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            cBack.senStr(result, strURL);
        } else {
            eBack.sendError("网络异常，请稍后重试");
        }
    }

}
