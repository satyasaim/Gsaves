package com.gsaves.media3.gsaves.app;

import android.content.Context;
import android.os.AsyncTask;

import com.gsaves.media3.gsaves.app.interfaces.AsyncTaskCompleteListener;

public class GenericAsyncTask extends AsyncTask<String, Void, String>
{
    private final Context context;
   Object finalResult;
    private AsyncTaskCompleteListener<String> callback;

    public GenericAsyncTask(Context context, AsyncTaskCompleteListener<String> cb) {
        this.context = context;
        this.callback = cb;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        finalResult=s;
        callback.onTaskComplete(s);
    }


}
